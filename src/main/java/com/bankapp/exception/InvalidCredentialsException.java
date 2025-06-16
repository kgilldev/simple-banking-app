package com.bankapp.exception;

import javax.naming.AuthenticationException;

public class InvalidCredentialsException extends RuntimeException {

        public InvalidCredentialsException(String msg) {
            super(msg);
        }
}
