package com.bankapp.service;

import com.bankapp.entity.Account;
import com.bankapp.entity.User;
import com.bankapp.enums.AccountType;
import com.bankapp.exception.InvalidCredentialsException;
import com.bankapp.repository.AccountRepository;
import com.bankapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    public void generateNonCheckingAccount(User user, AccountType accountType) {
        if (!userRepository.existsById(user.getId())) {
            throw new InvalidCredentialsException("User does not exist!");
        }

        Account account = Account.builder()
                .accountNumber(generateRandomAccountNumber())
                .accountType(accountType)
                .balance(BigDecimal.valueOf(100.0))
                .user(user)
                .build();

        accountRepository.save(account);

    }

    public void generateDefaultCheckingAccount(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new InvalidCredentialsException("User does not exist!");
        }

        Account account = Account.builder()
                .accountNumber(generateRandomAccountNumber())
                .accountType(AccountType.CHECKING)
                .balance(BigDecimal.valueOf(100.0))
                .user(user)
                .build();

        accountRepository.save(account);
    }

    public String generateRandomAccountNumber() {
        String accountNumber;

        do {
            accountNumber = String.valueOf(1000000000L + (long)(Math.random() * 9000000000L));
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());
        return accountNumber;
    }

}
