package com.communityrideshare.payment.web;

import com.communityrideshare.payment.domain.Transaction;
import com.communityrideshare.payment.service.PaymentService;
import com.communityrideshare.payment.web.dtos.TransactionRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Transaction> processTransaction(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = paymentService.processTransaction(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
