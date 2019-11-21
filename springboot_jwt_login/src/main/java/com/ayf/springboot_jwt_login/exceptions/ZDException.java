package com.ayf.springboot_jwt_login.exceptions;

public class ZDException extends Exception {

    public ZDException() {
    }

    public ZDException(String message) {
        super(message);
    }

    public ZDException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZDException(Throwable cause) {
        super(cause);
    }

    public ZDException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
