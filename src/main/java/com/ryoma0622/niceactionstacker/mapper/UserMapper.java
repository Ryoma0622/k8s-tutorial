package com.ryoma0622.niceactionstacker.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ryoma0622.niceactionstacker.entity.User;

@Mapper
@Repository
public interface UserMapper {

    int insert(User user);
    User selectBySlackId(String slackId);
}
