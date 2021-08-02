package com.splitwise.services.authentication;

import com.splitwise.models.User;
import com.splitwise.repositories.interfaces.UserRepository;

import java.util.Optional;

public class ConsoleAuthenticationContext implements AuthenticationContext {
    UserRepository userRepository;
    Long userId = 1l;
    public ConsoleAuthenticationContext(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getCurrentLoggedInUser() {
        return userRepository.findById(userId);
    }

    @Override
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
