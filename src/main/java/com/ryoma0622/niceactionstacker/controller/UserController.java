package com.ryoma0622.niceactionstacker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryoma0622.niceactionstacker.entity.User;
import com.ryoma0622.niceactionstacker.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public int create(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public User getBySlackId(@RequestParam("slackId") String slackId) {
        return userService.findBySlackId(slackId);
    }
}
