package com.example.springcloudclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;
@Configuration
public class MyWebSecurityConfigurer{
    private final ContextRefresher contextRefresher;
    private final Environment environment;
    @Autowired
    public MyWebSecurityConfigurer(ContextRefresher contextRefresher,
                                                  Environment environment) {
        this.contextRefresher = contextRefresher;
        this.environment = environment;
    }
    //@Scheduled(fixedRate = 3 * 1000, initialDelay = 3 * 1000)
    public void autoRefresh() {
        System.out.println("自动刷新");
        Set<String> updatedPropertyNames = contextRefresher.refresh();
        updatedPropertyNames.forEach( propertyName ->
                System.err.printf("[Thread :%s] 当前配置已更新，具体 Key：%s , Value : %s \n",
                        Thread.currentThread().getName(),
                        propertyName,
                        environment.getProperty(propertyName)
                ));
    }
}
