package com.digitalnewagency.task.service;

import com.digitalnewagency.task.api.dto.AccountDto;
import com.digitalnewagency.task.persistence.entity.Account;
import com.digitalnewagency.task.persistence.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public AccountDto addAccountForUser(UUID userId) {
        Account account = accountRepository.save(new Account(UUID.randomUUID(), userId, BigDecimal.ZERO));
        return new AccountDto(account.getAccountId(), account.getUserId(), account.getBalance());
    }

    public AccountDto getAccountForUser(UUID userId) {
        return accountRepository.findOneByUserId(userId).map(this::accountToAccountDto).orElseThrow();
    }

    @Transactional
    public AccountDto decreaseBalance(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findOneByAccountId(accountId).orElseThrow();
        BigDecimal balance = account.getBalance();
        account.setBalance(balance.subtract(amount));
        return accountToAccountDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto increaseBalance(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findOneByAccountId(accountId).orElseThrow();
        BigDecimal balance = account.getBalance();
        account.setBalance(balance.add(amount));
        return accountToAccountDto(accountRepository.save(account));
    }

    public AccountDto accountToAccountDto(Account account) {
        return new AccountDto(account.getAccountId(), account.getUserId(), account.getBalance());
    }
}
