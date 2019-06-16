package com.example.web.controller;

import com.example.domain.Message;
import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user/save")
    public Message saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/user/list")
    public Collection list(){
        Collection collection =userService.findAll();
        return collection;
    }
}
