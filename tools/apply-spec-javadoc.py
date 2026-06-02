#!/usr/bin/env python3
"""
Apply OpenAPI descriptions as Javadoc across prime-sdk-java models, requests,
responses, and service interfaces. Inserts only missing Javadoc blocks.
"""

from __future__ import annotations

import re
import sys
from pathlib import Path

try:
    import yaml
except ImportError:
    print("PyYAML required: python3 -m pip install pyyaml", file=sys.stderr)
    sys.exit(1)

REPO_ROOT = Path(__file__).resolve().parents[1]
SPEC_PATH = REPO_ROOT / "apiSpec" / "prime-public-spec.yaml"
JAVA_ROOT = REPO_ROOT / "src" / "main" / "java" / "com" / "coinbase" / "prime"

OPERATION_PREFIXES = ("PrimeRESTAPI_", "PrimeBeta_", "Beta_")


def escape_javadoc(text: str) -> str:
    if not text:
        return ""
    text = text.strip()
    text = re.sub(r"\s+", " ", text)
    return (
        text.replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("`", "&#x60;")
    )


def block_comment(text: str, indent: str = "    ") -> str:
    text = escape_javadoc(text)
    if not text:
        return ""
    return f"{indent}/**\n{indent} * {text}\n{indent} */\n"


def schema_short_key(full_name: str) -> str:
    return full_name.rsplit(".", 1)[-1] if "." in full_name else full_name


def operation_id_to_method_name(operation_id: str) -> str:
    name = operation_id
    for prefix in OPERATION_PREFIXES:
        if name.startswith(prefix):
            name = name[len(prefix) :]
            break
    return name[0].lower() + name[1:] if name else ""


def parse_enum_value_descriptions(description: str | None) -> dict[str, str]:
    if not description:
        return {}
    result: dict[str, str] = {}
    for line in description.splitlines():
        line = line.strip()
        if not line.startswith("- "):
            continue
        body = line[2:].strip()
        if ":" in body:
            key, _, desc = body.partition(":")
            result[key.strip()] = desc.strip()
    return result


def prop_description(prop: dict, schemas_raw: dict) -> str | None:
    if not isinstance(prop, dict):
        return None
    desc = prop.get("description") or prop.get("title")
    if desc:
        return desc
    ref = prop.get("$ref")
    if ref:
        key = schema_short_key(ref.rsplit("/", 1)[-1])
        target = schemas_raw.get(key) or schemas_raw.get(
            f"coinbase.public_rest_api.{key}"
        )
        if isinstance(target, dict):
            return target.get("description") or target.get("title")
    return None


def load_spec(path: Path) -> tuple[dict, dict, dict]:
    with path.open() as f:
        doc = yaml.safe_load(f)

    raw_schemas = doc.get("components", {}).get("schemas", {}) or {}
    schemas: dict[str, dict] = {}

    for full_name, schema in raw_schemas.items():
        if not isinstance(schema, dict):
            continue
        short = schema_short_key(full_name)
        entry = {
            "description": schema.get("description") or schema.get("title"),
            "properties": {},
            "enum_values": {},
        }
        if schema.get("enum"):
            entry["enum_values"] = parse_enum_value_descriptions(schema.get("description"))
            if not entry["description"]:
                entry["description"] = schema.get("title")
        for prop_name, prop in (schema.get("properties") or {}).items():
            desc = prop_description(prop, raw_schemas)
            if desc:
                entry["properties"][prop_name] = desc
        schemas[short] = entry

    operations: dict[str, dict] = {}
    for _path, methods in (doc.get("paths") or {}).items():
        if not isinstance(methods, dict):
            continue
        for method, op in methods.items():
            if method.startswith("x-") or not isinstance(op, dict):
                continue
            op_id = op.get("operationId")
            if not op_id:
                continue
            mname = operation_id_to_method_name(op_id)
            params: dict[str, str] = {}
            for param in op.get("parameters") or []:
                if isinstance(param, dict) and param.get("description"):
                    params[param["name"]] = param["description"]

            body_props: dict[str, str] = {}
            req = op.get("requestBody") or {}
            content = (req.get("content") or {}).get("application/json") or {}
            schema = content.get("schema") or {}
            if schema.get("properties"):
                for k, v in schema["properties"].items():
                    d = prop_description(v, raw_schemas)
                    if d:
                        body_props[k] = d

            response_ref = None
            for resp in (op.get("responses") or {}).values():
                if not isinstance(resp, dict):
                    continue
                content = (resp.get("content") or {}).get("application/json") or {}
                schema = content.get("schema") or {}
                if "$ref" in schema:
                    response_ref = schema_short_key(schema["$ref"].rsplit("/", 1)[-1])
                    break

            operations[mname] = {
                "summary": op.get("summary") or "",
                "description": op.get("description") or "",
                "parameters": params,
                "body_properties": body_props,
                "response_ref": response_ref,
            }

    return schemas, operations, raw_schemas


def build_java_service_index() -> dict[str, dict]:
    """java_method -> {request, response} class simple names."""
    index: dict[str, dict] = {}
    pattern = re.compile(
        r"(\w+Response)\s+(\w+)\s*\(\s*(\w+Request)\s+\w+\s*\)"
    )
    for path in JAVA_ROOT.glob("*/*Service.java"):
        text = path.read_text(encoding="utf-8")
        for resp, method, req in pattern.findall(text):
            index[method] = {"request": req, "response": resp, "service": path.parent.name}
    return index


def normalize_sdk_type_name(name: str) -> str:
    """Align SDK type names with OpenAPI schema names (e.g. drop path-specific Portfolio segment)."""
    for token in ("Portfolio", "Entity", "Prime"):
        name = name.replace(token, "")
    return name


def find_operation_by_response_type(java_response: str | None, operations: dict) -> dict | None:
    if not java_response:
        return None
    norm_java = normalize_sdk_type_name(java_response)
    for op in operations.values():
        ref = op.get("response_ref")
        if ref and normalize_sdk_type_name(ref) == norm_java:
            return op
    return None


def link_class_to_operation(
    class_name: str, service_index: dict, operations: dict
) -> dict | None:
    for _method, info in service_index.items():
        if class_name == info.get("response"):
            return find_operation_by_response_type(class_name, operations)
        if class_name == info.get("request"):
            return find_operation_by_response_type(info.get("response"), operations)
    return None


def has_javadoc_before(text: str, pos: int) -> bool:
    snippet = text[:pos].rstrip()
    return snippet.endswith("*/")


def extract_json_property_name(annotation_block: str) -> str | None:
    m = re.search(r'value\s*=\s*"([^"]+)"', annotation_block)
    if m:
        return m.group(1)
    m = re.search(r'@JsonProperty\("([^"]+)"\)', annotation_block)
    return m.group(1) if m else None


def insert_class_javadoc(text: str, class_name: str, description: str | None) -> str:
    if not description:
        return text
    pattern = rf"(public (?:class|enum|interface) {re.escape(class_name)}\s*\{{)"
    m = re.search(pattern, text)
    if not m or has_javadoc_before(text, m.start()):
        return text
    return text[: m.start()] + block_comment(description) + text[m.start() :]


def existing_field_javadoc(text: str, pos: int) -> tuple[int, str] | None:
    """Return (start, inner_text) of the Javadoc block immediately before pos."""
    before = text[:pos]
    trimmed = before.rstrip()
    if not trimmed.endswith("*/"):
        return None
    start = trimmed.rfind("/**")
    if start == -1:
        return None
    block = trimmed[start:]
    if re.search(r"\bpublic\s+(?:class|enum|interface)\b", block):
        return None
    if not re.fullmatch(r"/\*\*[\s\S]*?\*/", block.strip(), re.DOTALL):
        return None
    after_close = before[trimmed.rfind("*/") + 2 : pos]
    if after_close.strip():
        return None
    inner_parts: list[str] = []
    for line in block.splitlines():
        m = re.match(r"^\s*\*\s?(.*)$", line)
        if m:
            inner_parts.append(m.group(1).strip())
    inner = " ".join(p for p in inner_parts if p)
    return start, inner


def insert_field_javadocs(text: str, properties: dict[str, str]) -> str:
    if not properties:
        return text

    pattern = re.compile(
        r"(?P<indent>^[ \t]*)(?P<block>(?:@\w+(?:\([^)]*\))?\s*\n[ \t]*)+?)"
        r"(?P<decl>private [^;]+;)",
        re.MULTILINE,
    )

    matches = []
    for match in pattern.finditer(text):
        block = match.group("block")
        if "@JsonProperty" not in block:
            continue
        prop_name = extract_json_property_name(block)
        if not prop_name or prop_name not in properties:
            continue
        matches.append((match, prop_name))

    for match, prop_name in reversed(matches):
        spec_desc = escape_javadoc(properties[prop_name])
        existing = existing_field_javadoc(text, match.start())
        if existing and existing[1] == spec_desc:
            continue
        if existing:
            text = (
                text[: existing[0]]
                + block_comment(properties[prop_name], match.group("indent"))
                + text[match.start() :]
            )
        elif has_javadoc_before(text, match.start()):
            continue
        else:
            text = (
                text[: match.start()]
                + block_comment(properties[prop_name], match.group("indent"))
                + text[match.start() :]
            )

    return text


def insert_enum_constant_javadocs(text: str, value_descriptions: dict[str, str]) -> str:
    if not value_descriptions:
        return text
    lines = text.splitlines(keepends=True)
    out: list[str] = []
    for line in lines:
        m = re.match(r"^(\s*)([A-Z][A-Z0-9_]*)(\s*,?\s*)$", line.rstrip("\n"))
        if m and m.group(2) in value_descriptions:
            pos = sum(len(l) for l in out)
            if not has_javadoc_before("".join(out), pos):
                out.append(block_comment(value_descriptions[m.group(2)], m.group(1)))
        out.append(line)
    return "".join(out)


def insert_service_method_javadocs(text: str, operations: dict, service_index: dict) -> str:
    pattern = re.compile(
        r"(?P<indent>^[ \t]*)(?P<sig>(\w+Response)\s+(\w+)\s*\(\s*(\w+Request)\s+\w+\s*\)\s*[^;]+;)\s*$",
        re.MULTILINE,
    )

    def replacer(match: re.Match) -> str:
        if has_javadoc_before(text, match.start()):
            return match.group(0)
        method = match.group(4)
        response_class = match.group(3)
        op = find_operation_by_response_type(response_class, operations)
        if not op:
            return match.group(0)
        doc = op.get("summary") or op.get("description")
        if not doc:
            return match.group(0)
        return block_comment(doc, match.group("indent")) + match.group("sig") + "\n"

    return pattern.sub(replacer, text)


def properties_for_class(
    class_name: str,
    schemas: dict,
    service_index: dict,
    operations: dict,
) -> dict[str, str]:
    props: dict[str, str] = {}
    schema = schemas.get(class_name)
    if schema:
        props.update(schema.get("properties") or {})

    op = link_class_to_operation(class_name, service_index, operations)
    if op:
        if class_name.endswith("Request"):
            props.update(op.get("parameters") or {})
            props.update(op.get("body_properties") or {})
        elif class_name.endswith("Response") and op.get("response_ref"):
            ref = schemas.get(op["response_ref"])
            if ref:
                props.update(ref.get("properties") or {})
    return props


def class_description_for(
    class_name: str, schemas: dict, service_index: dict, operations: dict
) -> str | None:
    schema = schemas.get(class_name)
    if schema and schema.get("description"):
        return schema["description"]
    op = link_class_to_operation(class_name, service_index, operations)
    if op:
        return op.get("summary") or op.get("description")
    return None


def process_java_file(
    path: Path, schemas: dict, operations: dict, service_index: dict
) -> bool:
    text = path.read_text(encoding="utf-8")
    original = text

    m = re.search(r"public (?:class|enum|interface) (\w+)", text)
    if not m:
        return False
    class_name = m.group(1)

    desc = class_description_for(class_name, schemas, service_index, operations)
    text = insert_class_javadoc(text, class_name, desc)

    props = properties_for_class(class_name, schemas, service_index, operations)
    text = insert_field_javadocs(text, props)

    if "public enum" in text:
        schema = schemas.get(class_name)
        if schema:
            text = insert_enum_constant_javadocs(text, schema.get("enum_values") or {})

    if class_name.endswith("Service") and "interface" in text:
        text = insert_service_method_javadocs(text, operations, service_index)

    if text != original:
        path.write_text(text, encoding="utf-8")
        return True
    return False


def main() -> int:
    if not SPEC_PATH.exists():
        print(f"Spec missing: {SPEC_PATH}", file=sys.stderr)
        return 1

    schemas, operations, _ = load_spec(SPEC_PATH)
    service_index = build_java_service_index()

    targets: list[Path] = []
    for pattern in (
        "model/**/*.java",
        "*/**/*Request.java",
        "*/**/*Response.java",
        "*/*Service.java",
    ):
        targets.extend(JAVA_ROOT.glob(pattern))

    changed: list[str] = []
    seen: set[Path] = set()
    for path in sorted(targets):
        if path in seen or "ServiceImpl" in path.name or "Test" in path.name:
            continue
        seen.add(path)
        if process_java_file(path, schemas, operations, service_index):
            changed.append(str(path.relative_to(REPO_ROOT)))

    print(f"Updated {len(changed)} files")
    for p in changed:
        print(f"  {p}")
    return 0


if __name__ == "__main__":
    sys.exit(main())
