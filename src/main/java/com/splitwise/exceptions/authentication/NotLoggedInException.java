package com.splitwise.exceptions.authentication;

public class NotLoggedInException extends AuthenticationException {
    public NotLoggedInException(String please_login_in) {
        super(please_login_in);
    }
}
