package com.splitwise.services.authentication;

public interface PasswordEncoder {

    String encode(String password,String username);
}
