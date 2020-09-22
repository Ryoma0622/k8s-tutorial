package com.ryoma0622.niceactionstacker.service;

import org.springframework.stereotype.Service;

import com.ryoma0622.niceactionstacker.entity.StackedMessage;
import com.ryoma0622.niceactionstacker.mapper.StackedMessageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StackedMessageService {

    private final StackedMessageMapper stackedMessageMapper;

    public int save(StackedMessage stackedMessage) {
        return stackedMessageMapper.insert(stackedMessage);
    }

    public StackedMessage findBySlackIdAndPath(String slackId, String path) {
        return stackedMessageMapper.selectBySlackIdAndPath(slackId, path);
    }
}
