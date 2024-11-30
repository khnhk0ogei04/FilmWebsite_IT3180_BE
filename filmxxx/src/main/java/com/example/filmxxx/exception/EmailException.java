package com.example.filmxxx.exception;

public class EmailException {
    public static class ErrorSendEmailException extends Exception {
        public ErrorSendEmailException(String message) {
            super(message);
        }
    }
}
