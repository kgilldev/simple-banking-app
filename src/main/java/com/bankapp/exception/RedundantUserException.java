package com.bankapp.exception;

public class RedundantUserException extends RuntimeException {

        public RedundantUserException(String msg) {
            super(msg);
        }

}
