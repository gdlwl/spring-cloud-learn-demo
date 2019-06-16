package com.example.springcloudclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class RefreshController {
    @Value("${my.name}")
    private String name;
    @GetMapping("/getName")
    public String getMyName(){
        return name;
    }
}
