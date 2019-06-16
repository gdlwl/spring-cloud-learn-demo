package com.example.hystrix;

import com.example.domain.User;
import com.example.service.UserService;
import com.sun.javafx.util.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**circuit breaker
 * 断路器
 */
public class UserFallback implements UserService {

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }
}
