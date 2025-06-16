package com.bankapp.service;

import com.bankapp.dto.Response.ModelResponse;
import com.bankapp.entity.Account;
import com.bankapp.entity.Transaction;
import com.bankapp.enums.TransactionType;
import com.bankapp.exception.AccountNotFoundException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public ModelResponse deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        if (amount.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal accountBalance = account.getBalance();
            account.setBalance(accountBalance.add(amount));
            accountRepository.save(account);

            Transaction transaction = Transaction.builder()
                    .transactionType(TransactionType.DEPOSIT)
                    .amount(amount)
                    .account(account)
                    .successful(true)
                    .build();

            transactionRepository.save(transaction);

            return ModelResponse.success(HttpStatus.OK);
        } else {
            return ModelResponse.failure(HttpStatus.BAD_REQUEST);
        }
    }

    public ModelResponse withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));

        if (amount.compareTo(BigDecimal.ZERO) > 0
                && account.getBalance().compareTo(amount) >= 0) {

            BigDecimal accountBalance = account.getBalance();
            account.setBalance(accountBalance.subtract(amount));
            accountRepository.save(account);

            Transaction transaction = Transaction.builder()
                    .transactionType(TransactionType.WITHDRAW)
                    .amount(amount)
                    .account(account)
                    .successful(true)
                    .build();

            transactionRepository.save(transaction);

            return ModelResponse.success(HttpStatus.OK);
        } else {

            return ModelResponse.failure(HttpStatus.BAD_REQUEST);
        }
    }
}
