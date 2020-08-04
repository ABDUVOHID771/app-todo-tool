package com.example.todotool.exception;

public class DuplicateException extends BaseException {

    public DuplicateException(String identifier) {
        super("Identifier : " + identifier + " is already exists");
    }

}
