package com.splitwise.exceptions.validations;

public class DuplicateUsernameException extends ValidationException {
    public DuplicateUsernameException(String username_already_exists) {
        super(username_already_exists);
    }
}
