package com.krasn.lab.exception;

import lombok.Getter;

public class InvalidTransactionException extends RuntimeException {

    @Getter
    private final String error;

    public InvalidTransactionException(final String error) {
        super(error);
        this.error = error;
    }

}
