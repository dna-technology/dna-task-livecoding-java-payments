package com.digitalnewagency.task.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDto {

    private UUID paymentId;
    private UUID userId;
    private UUID merchantId;
    private BigDecimal amount;

    public PaymentDto() {
    }

    public PaymentDto(UUID paymentId, UUID userId, UUID merchantId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.merchantId = merchantId;
        this.amount = amount;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(UUID merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
