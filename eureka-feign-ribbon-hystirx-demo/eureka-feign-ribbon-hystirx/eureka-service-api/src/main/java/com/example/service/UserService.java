package com.example.service;

import com.example.domain.User;
import com.example.hystrix.UserFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value="eureka-service-provider",fallback = UserFallback.class)
public interface UserService {
    @PostMapping("user/save")
    boolean save(User user);

    @GetMapping("user/list")
    List<User> findAll();
}
