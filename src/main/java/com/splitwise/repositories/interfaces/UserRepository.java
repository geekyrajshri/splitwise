package com.splitwise.repositories.interfaces;

import com.splitwise.models.User;
import com.splitwise.repositories.interfaces.IRepository;

import java.util.Optional;

public interface UserRepository extends IRepository<User,Long> {

public Optional<User> findByUsername(String username);
}
