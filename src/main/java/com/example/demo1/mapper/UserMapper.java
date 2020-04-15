package com.example.demo1.mapper;

import com.example.demo1.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified,avatar_url) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user u where u.id = #{creator}")
    User queryById(@Param("creator") int creator);

    @Select("select * from user u where u.token = #{token}")
    User getUser(@RequestParam(name = "token") String token);




}