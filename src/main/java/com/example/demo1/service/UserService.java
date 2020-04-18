package com.example.demo1.service;

import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public void CreateOrUpdate(User user) {

        User dbuser = userMapper.queryByAccountId(user.getAccountId());

        if(dbuser != null){
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateUser(user);
        }else{
            userMapper.insert(user);
        }

    }

    public User queryUserByAccountId(String accountId) {
        User user = userMapper.queryByAccountId(accountId);

        return user;
    }
}
