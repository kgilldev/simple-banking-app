package com.bankapp.service;

import com.bankapp.dto.Response.AuthResponse;
import com.bankapp.dto.Request.LoginRequest;
import com.bankapp.dto.Request.RegisterRequest;
import com.bankapp.exception.InvalidCredentialsException;
import com.bankapp.exception.RedundantUserException;
import com.bankapp.entity.User;
import com.bankapp.repository.UserRepository;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public AuthService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String username = registerRequest.getUsername();


        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            throw new RedundantUserException("Username or email already exists!");
        }

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User newUser = User.builder()
                .username(username)
                .email(email)
                .password(hashedPassword)
                .build();

        userRepository.save(newUser);
        accountService.generateDefaultCheckingAccount(newUser);
        return new AuthResponse("User successfully created!");
    }

    public AuthResponse login(LoginRequest loginRequest) {
        String userNameOrEmail = loginRequest.getUsernameOrEmail();
        String rawPassword = loginRequest.getPassword();

        User user = userRepository.findByUsername(userNameOrEmail)
                .orElse(userRepository.findByEmail(userNameOrEmail)
                        .orElse(null));

        if (user == null){
            throw new InvalidCredentialsException("Invalid Username or Password!");
        }

        String encodedPassword = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPassword) ) {
            throw new InvalidCredentialsException("Invalid Password");
        }

        return new AuthResponse("Successful login!");
        }

}
