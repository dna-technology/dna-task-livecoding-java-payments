package com.digitalnewagency.task.api.controller;

import com.digitalnewagency.task.api.dto.PaymentDto;
import com.digitalnewagency.task.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.digitalnewagency.task.api.controller.PaymentController.PATH;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(PATH)
public class PaymentController {

    public static final String PATH = "/transactions";

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public PaymentDto addPayment(@RequestBody PaymentDto paymentDto) throws Exception {
        return paymentService.addPayment(paymentDto);
    }
}
