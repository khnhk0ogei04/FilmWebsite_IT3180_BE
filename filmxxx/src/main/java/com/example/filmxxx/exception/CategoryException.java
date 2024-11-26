package com.example.filmxxx.exception;

public class CategoryException {
    public static class CategoryNotFoundException extends Exception {
        public CategoryNotFoundException(String message) {
            super(message);
        }
    }

    public static class CategoryDuplicatedException extends Exception {
        public CategoryDuplicatedException(String message) {
            super(message);
        }
    }
}
