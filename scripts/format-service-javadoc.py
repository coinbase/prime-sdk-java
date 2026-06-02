#!/usr/bin/env python3
"""Apply structured Javadoc to *Service.java interface methods from OpenAPI spec."""

from __future__ import annotations

import re
import sys
from pathlib import Path

try:
    import yaml
except ImportError:
    sys.exit("PyYAML required: pip install pyyaml")

ROOT = Path(__file__).resolve().parents[1]
SPEC_PATH = ROOT / "apiSpec" / "prime-public-spec.yaml"
SRC = ROOT / "src" / "main" / "java" / "com" / "coinbase" / "prime"

CLIENT_EX = "CoinbaseClientException"
PRIME_EX = "CoinbasePrimeException"

METHOD_RE = re.compile(
    r"(?P<block>(?:/\*\*(?:(?!\*/).)*\*/\s*)*)"
    r"(?P<annotations>(?:@\w+(?:\([^)]*\))?\s+)*)"
    r"(?P<ret>[\w<>,\s]+?)\s+"
    r"(?P<name>\w+)\s*"
    r"\((?P<params>[^)]*)\)\s*"
    r"throws\s+(?P<throws>[^;]+);",
    re.DOTALL,
)


def load_ops() -> tuple[dict[str, dict], dict[str, dict]]:
    spec = yaml.safe_load(SPEC_PATH.read_text())
    by_summary: dict[str, dict] = {}
    by_method: dict[str, dict] = {}
    for _path, methods in spec.get("paths", {}).items():
        for method, op in methods.items():
            if method.startswith("x-") or not isinstance(op, dict):
                continue
            summary = (op.get("summary") or "").strip()
            if summary:
                by_summary[summary] = op
            op_id = op.get("operationId") or ""
            m = re.sub(r"^PrimeRESTAPI_", "", op_id)
            if m:
                by_method[m[0].lower() + m[1:]] = op
    return by_summary, by_method


def class_javadoc(java_path: Path) -> str | None:
    if not java_path.exists():
        return None
    text = java_path.read_text()
    m = re.search(r"/\*\*\s*\n\s*\*\s*(.+?)\s*\n\s*\*/", text, re.DOTALL)
    if not m:
        return None
    first = m.group(1).split("\n")[0].strip()
    return re.sub(r"^\*\s?", "", first).strip() or None


def wrap_lines(text: str, width: int = 88) -> list[str]:
    words = text.split()
    if not words:
        return []
    lines: list[str] = []
    current: list[str] = []
    for word in words:
        candidate = " ".join(current + [word])
        if len(candidate) > width and current:
            lines.append(" ".join(current))
            current = [word]
        else:
            current.append(word)
    if current:
        lines.append(" ".join(current))
    return lines


def needs_paragraph(summary: str, description: str) -> bool:
    if not description:
        return False
    return description.rstrip(".").lower() != summary.rstrip(".").lower()


def parse_deprecated(javadoc: str) -> list[str]:
    if not javadoc:
        return []
    lines: list[str] = []
    for raw in javadoc.splitlines():
        line = raw.strip()
        if line.startswith("* @deprecated"):
            lines.append(line[2:].strip())
        elif line.startswith("* —") or line.startswith("* -"):
            lines.append(line[2:].strip())
    return lines


def build_javadoc(
    summary: str,
    description: str,
    param_name: str | None,
    deprecated_lines: list[str],
) -> str:
    out = ["    /**", f"     * {summary.rstrip('.')}."]

    if needs_paragraph(summary, description):
        out.append("     * <p>")
        for line in wrap_lines(description.rstrip(".")):
            out.append(f"     * {line}")
        out.append("     * </p>")

    if deprecated_lines:
        out.append("     *")
    for dep in deprecated_lines:
        out.append(f"     * {dep}")

    out.append("     *")

    if param_name:
        out.append(f"     * @param {param_name} the request parameters for this operation")

    out.append("     * @return the response payload for this operation")
    out.extend(
        [
            f"     * @throws {CLIENT_EX} if the request fails client-side validation",
            f"     * @throws {PRIME_EX} if the Prime API returns an error response",
            "     */",
        ]
    )
    return "\n".join(out)


def process_service_file(path: Path, ops_by_summary: dict[str, dict], ops_by_method: dict[str, dict]) -> bool:
    text = path.read_text()
    pkg_match = re.search(r"package\s+([\w.]+);", text)
    if not pkg_match:
        return False
    pkg_dir = ROOT / "src" / "main" / "java" / pkg_match.group(1).replace(".", "/")

    parts: list[str] = []
    last = 0
    changed = False

    for match in METHOD_RE.finditer(text):
        name = match.group("name")
        if name[0].isupper():
            continue

        parts.append(text[last : match.start()])

        ret_type = match.group("ret").strip()
        params = match.group("params").strip()
        annotations = (match.group("annotations") or "").rstrip()
        throws = match.group("throws").strip()
        existing = match.group("block") or ""

        deprecated_lines = parse_deprecated(existing)
        param_name = None
        summary = None
        description = ""
        op = ops_by_method.get(name)

        if op:
            summary = (op.get("summary") or "").strip() or None
            description = (op.get("description") or "").strip()

        if params:
            bits = params.split()
            if len(bits) >= 2:
                param_name = bits[-1]
                req_type = bits[-2]
                req_summary = class_javadoc(pkg_dir / f"{req_type}.java")
                if req_summary:
                    summary = req_summary
                    if not description:
                        description = (
                            ops_by_summary.get(req_summary, {}).get("description") or ""
                        ).strip()

        if not summary:
            summary = class_javadoc(pkg_dir / f"{ret_type}.java")

        if not summary and existing:
            m = re.search(r"\*\s*(?!@)(.+)", existing)
            summary = m.group(1).strip() if m else None

        if not summary:
            parts.append(match.group(0))
            last = match.end()
            continue

        if not description:
            description = (ops_by_summary.get(summary, {}).get("description") or "").strip()
        block = build_javadoc(summary, description, param_name, deprecated_lines)

        method = f"{ret_type} {name}({params}) throws {throws};"
        segment = block + "\n"
        if annotations:
            segment += f"    {annotations}\n"
        segment += f"    {method}\n"
        parts.append(segment)
        last = match.end()
        changed = True

    if not changed:
        return False

    parts.append(text[last:])
    result = "".join(parts)
    result = re.sub(r"(public interface \w+ \{)[ \t]*", r"\1\n", result)
    result = re.sub(r"^/\*\*", "    /**", result, flags=re.MULTILINE)
    result = re.sub(r"^        /\*\*", "    /**", result, flags=re.MULTILINE)
    path.write_text(result)
    return True


def main() -> int:
    ops_by_summary, ops_by_method = load_ops()
    updated = 0
    for path in sorted(SRC.glob("*/*Service.java")):
        if process_service_file(path, ops_by_summary, ops_by_method):
            print(f"updated {path.relative_to(ROOT)}")
            updated += 1
    print(f"done: {updated} service files updated")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
