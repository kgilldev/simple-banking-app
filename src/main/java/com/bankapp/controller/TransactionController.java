package com.bankapp.controller;

import com.bankapp.dto.Request.TransactionRequest;
import com.bankapp.dto.Response.ModelResponse;
import com.bankapp.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/accounts/{accountNumber}/deposit")
    public ResponseEntity<ModelResponse> deposit(
            @PathVariable String accountNumber,
            @Valid @RequestBody TransactionRequest transactionRequest) {

        ModelResponse modelResponse = transactionService.deposit(accountNumber, transactionRequest.getAmount());
        return ResponseEntity.status(modelResponse.getStatus()).body(modelResponse);
    }

    @PostMapping("/accounts/{accountNumber}/withdraw")
    public ResponseEntity<ModelResponse> withdraw(
            @PathVariable String accountNumber,
            @Valid @RequestBody TransactionRequest transactionRequest) {

        ModelResponse modelResponse = transactionService.withdraw(accountNumber, transactionRequest.getAmount());
        return ResponseEntity.status(modelResponse.getStatus()).body(modelResponse);
    }
}
