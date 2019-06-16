package com.example.repository;

import com.example.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
@Repository
public class UserRepository {
    private final ConcurrentHashMap<Long, User>  userConcurrentHashMap = new ConcurrentHashMap<>();

    public boolean save(User user){
        if (user==null){
            return false;
        }
        return userConcurrentHashMap.putIfAbsent(user.getId(),user)==null;
    }
    public List<User> findAll(){
        Enumeration keys = userConcurrentHashMap.keys();
        List<User> list = new ArrayList();
        while (keys.hasMoreElements()){
            list.add(userConcurrentHashMap.get(keys.nextElement()));
        }
       return list;
    }
}
