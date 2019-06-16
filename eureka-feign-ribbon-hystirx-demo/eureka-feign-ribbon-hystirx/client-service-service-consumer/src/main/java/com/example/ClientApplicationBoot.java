package com.example;

import com.example.config.RibbonConfig;
import com.example.service.UserService;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableFeignClients(clients = UserService.class)
@EnableEurekaClient
@RibbonClient(value ="MyBalanceRule",configuration = RibbonConfig.MyBalanceRule.class)
@EnableHystrix
public class ClientApplicationBoot {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplicationBoot.class,args);
    }
}
