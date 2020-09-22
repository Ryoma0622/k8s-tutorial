package com.ryoma0622.niceactionstacker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryoma0622.niceactionstacker.entity.StackedMessage;
import com.ryoma0622.niceactionstacker.service.StackedMessageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/stacked_messages")
@Slf4j
@RequiredArgsConstructor
public class StackedMessageController {

    private final StackedMessageService stackedMessageService;

    @PostMapping
    public int create(@RequestBody StackedMessage stackedMessage) {
        return stackedMessageService.save(stackedMessage);
    }

    @GetMapping
    public StackedMessage getBySlackIdAndPath(@RequestParam("slackId") String slackId, @RequestParam("path") String path) {
        return stackedMessageService.findBySlackIdAndPath(slackId, path);
    }
}
