package com.digitalnewagency.task.api;

import com.digitalnewagency.task.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseStatus(NO_CONTENT)
    @PostMapping
    void saveTransactionLog(@RequestBody TransactionDto transactionDto) {
        transactionService.addCase(transactionDto);
    }

}
