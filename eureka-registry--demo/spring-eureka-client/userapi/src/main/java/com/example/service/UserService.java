package com.example.service;

import com.example.domain.Message;
import com.example.domain.User;

import java.util.Collection;

public interface UserService {
    Message save(User user);

    Collection<User> findAll();
}
