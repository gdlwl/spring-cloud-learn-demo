package com.example.web.controller;

import com.example.domain.User;
import com.example.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {
    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user/save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }
    @GetMapping("/user/list")
    public List<User> findAll() {
        return userService.findAll();
    }
}
