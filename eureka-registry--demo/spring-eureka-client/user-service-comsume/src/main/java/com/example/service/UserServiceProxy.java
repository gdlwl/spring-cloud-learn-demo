package com.example.service;

import com.example.domain.Message;
import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
@Service
public class UserServiceProxy implements UserService{
    private static final String PROVIDER_SERVER_URL_PREFIX = "http://user-service-provider";
    /**
     * 通过 REST API 代理到服务器提供者
     */
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Message save(User user) {
        return restTemplate.postForObject(PROVIDER_SERVER_URL_PREFIX + "/user/save", user, Message.class);
    }

    @Override
    public Collection<User> findAll() {
        return restTemplate.getForObject(PROVIDER_SERVER_URL_PREFIX + "/user/list",Collection.class);
    }
}
