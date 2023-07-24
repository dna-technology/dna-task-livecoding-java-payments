package com.digitalnewagency.task.api.dto;

import java.util.UUID;

public class MerchantDto {

    private UUID merchantId;

    private String name;

    public MerchantDto() {
    }

    public MerchantDto(UUID merchantId, String name) {
        this.merchantId = merchantId;
        this.name = name;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}