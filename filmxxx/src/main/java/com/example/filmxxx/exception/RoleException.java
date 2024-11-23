package com.example.filmxxx.exception;

public class RoleException{
    public static class RoleNotFoundException extends RuntimeException{
        public RoleNotFoundException(Long id){
            super("Role with id " + id + " not found");
        }
    }
}
