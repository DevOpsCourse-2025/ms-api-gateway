package com.chiops.gateway.libs.exceptions.exception;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException(String message) {
        super(message);
    }
}