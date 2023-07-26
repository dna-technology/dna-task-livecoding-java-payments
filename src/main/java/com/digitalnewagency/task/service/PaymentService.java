package com.digitalnewagency.task.service;

import com.digitalnewagency.task.api.dto.AccountDto;
import com.digitalnewagency.task.api.dto.MerchantDto;
import com.digitalnewagency.task.api.dto.PaymentDto;
import com.digitalnewagency.task.api.dto.UserDto;
import com.digitalnewagency.task.persistence.entity.Payment;
import com.digitalnewagency.task.persistence.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {

    private final UserService userService;
    private final MerchantService merchantService;
    private final AccountService accountService;
    private final PaymentRepository paymentRepository;

    public PaymentService(
            UserService userService, MerchantService merchantService, AccountService accountService,
            PaymentRepository paymentRepository) {
        this.userService = userService;
        this.merchantService = merchantService;
        this.accountService = accountService;
        this.paymentRepository = paymentRepository;
    }

    public PaymentDto addPayment(PaymentDto paymentDto) throws Exception {

        UserDto userDto = userService.getUser(paymentDto.getUserId());
        MerchantDto merchantDto = merchantService.getMerchant(paymentDto.getMerchantId());
        AccountDto accountDto = accountService.getAccountForUser(paymentDto.getUserId());

        if (accountDto.getBalance().compareTo(paymentDto.getAmount()) < 0) {
            throw new Exception("insufficient funds");
        }

        accountService.decreaseBalance(accountDto.getAccountId(), paymentDto.getAmount());
        Payment payment = paymentRepository.save(
                new Payment(UUID.randomUUID(), userDto.getUserId(), merchantDto.getMerchantId(),
                        paymentDto.getAmount()));

        return new PaymentDto(payment.getPaymentId(), payment.getUserId(), payment.getMerchantId(),
                payment.getAmount());
    }
}
