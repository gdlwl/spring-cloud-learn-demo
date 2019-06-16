package com.example.repository;

import com.example.domain.Message;
import com.example.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private ConcurrentHashMap<Long, User> repository = new ConcurrentHashMap();
    public Message save(User user){
        Message msg = new Message();
        if(repository.putIfAbsent(user.getId(),user)==null){
            msg.setCode(1);
            msg.setMessage("增加成功!");
        }else {
            msg.setCode(0);
            msg.setMessage("增加失败!");
        }
        return msg;
    }

    public Collection<User> findAll(){
        return repository.values();
    }

}

