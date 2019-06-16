package com.example.service;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
   public Message save(User user){
        return userRepository.save(user);
    }
    @Override
    public Collection<User> findAll(){
        return userRepository.findAll();
    }
}
