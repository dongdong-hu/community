package com.example.demo1.service;

import com.example.demo1.mapper.UserMapper;
import com.example.demo1.model.User;
import com.example.demo1.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    public void CreateOrUpdate(User user) {

        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> list =  userMapper.selectByExample(example);
        User  dbuser = list.get(0);
       // User dbuser = userMapper.queryByAccountId(user.getAccountId());

        if(dbuser != null){
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateByExampleSelective(user,new UserExample());
           // userMapper.updateUser(user);
        }else{
            userMapper.insert(user);
        }

    }

    public User queryUserByAccountId(String accountId) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(accountId);
         List<User> list =  userMapper.selectByExample(example);
         User user =  list.get(0);
       // User user = userMapper.queryByAccountId(accountId);

        return user;
    }
}
