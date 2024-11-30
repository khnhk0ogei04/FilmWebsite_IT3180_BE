package com.example.filmxxx.exception;

public class UserException{
    public static class UserNotFoundException extends RuntimeException{
        public UserNotFoundException(Long id){
            super("User with id " + id + " not found");
        }
    }

    public static class UsernameAlreadyExistsException extends RuntimeException {
        public UsernameAlreadyExistsException(String username) {
            super("Username " + username + " already exists");
        }
    }

    public static class InvalidEmailFormatException extends RuntimeException {
        public InvalidEmailFormatException(String email) {
            super("Invalid email format: " + email);
        }
    }

    public static class InvalidMoneyException extends RuntimeException {
        public InvalidMoneyException(Long money) {
            super("Invalid money, money must be a positive integer");
        }
    }

    public static class InvalidTokenException extends RuntimeException {
        public InvalidTokenException(String token) {
            super("Invalid token: " + token);
        }
    }

    public static class ExpiredTokenException extends RuntimeException {
        public ExpiredTokenException(String token) {
            super("Expired token: " + token);
        }
    }

    public static class InvalidEmailException extends RuntimeException {
        public InvalidEmailException(String email) {
            super("Invalid email: " + email);
        }
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String password) {
            super("Invalid password: " + password);
        }
    }
}
