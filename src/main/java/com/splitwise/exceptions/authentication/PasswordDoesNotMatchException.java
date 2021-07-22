package com.splitwise.exceptions.authentication;

import com.splitwise.exceptions.authentication.AuthenticationException;

public class PasswordDoesNotMatchException extends AuthenticationException {
    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
