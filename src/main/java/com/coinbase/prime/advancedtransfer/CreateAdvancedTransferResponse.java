/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.coinbase.prime.advancedtransfer;

import com.coinbase.prime.model.AdvancedTransfer;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CreateAdvancedTransferResponse is the response after creating an advanced transfer.
 */
public class CreateAdvancedTransferResponse {
    /**
     * AdvancedTransfer represents a complex transfer operation such as a blind match settlement.
     */
    @JsonProperty("advanced_transfer")
    private AdvancedTransfer advancedTransfer;

    public CreateAdvancedTransferResponse() {
    }

    public AdvancedTransfer getAdvancedTransfer() {
        return advancedTransfer;
    }

    public void setAdvancedTransfer(AdvancedTransfer advancedTransfer) {
        this.advancedTransfer = advancedTransfer;
    }

}
