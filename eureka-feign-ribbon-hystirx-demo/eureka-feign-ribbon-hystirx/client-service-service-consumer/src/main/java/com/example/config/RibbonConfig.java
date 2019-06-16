package com.example.config;

import com.example.hystrix.UserFallback;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
public class RibbonConfig {
    @Bean
    public MyBalanceRule getMyBalanceRule(){
        return  new MyBalanceRule();
    }

    /**
     * 我的负载均衡策略，返回第一个可达服务
     */
    public static class MyBalanceRule extends AbstractLoadBalancerRule{
        @Override
        public void initWithNiwsConfig(IClientConfig iClientConfig) {

        }

        @Override
        public Server choose(Object o) {
            ILoadBalancer lb = this.getLoadBalancer();
            if(lb==null){
                return null;
            }
            List upList = lb.getReachableServers();
            if(CollectionUtils.isEmpty(upList)){
                return null;
            }
            System.out.println("返回第一个可达服务");
            return (Server)upList.get(0);
        }
    }
}
