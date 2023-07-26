package com.digitalnewagency.task.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountDto {

    private UUID accountId;

    private UUID userId;

    private BigDecimal balance;

    public AccountDto() {
    }

    public AccountDto(UUID accountId, UUID userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
