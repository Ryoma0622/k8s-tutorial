package com.ryoma0622.niceactionstacker.service;

import org.springframework.stereotype.Service;

import com.ryoma0622.niceactionstacker.entity.User;
import com.ryoma0622.niceactionstacker.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public int save(User user) {
        return userMapper.insert(user);
    }

    public User findBySlackId(String slackId) {
        return userMapper.selectBySlackId(slackId);
    }
}
