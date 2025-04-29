package com.chiops.gateway.libs.exceptions.exception;

public class PermissionDeniedException extends RuntimeException {
    public PermissionDeniedException(String message) {
        super(message);
    }
}