package com.digitalnewagency.task.service;

import com.digitalnewagency.task.api.TransactionDto;
import com.digitalnewagency.task.persistence.Transaction;
import com.digitalnewagency.task.persistence.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionService {

    TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setUserId(transactionDto.getUserId());
        transactionRepository.save(transaction);
    }


}
