package com.ryoma0622.niceactionstacker.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.response.users.UsersInfoResponse;

import com.ryoma0622.niceactionstacker.properties.SlackBotProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SlackAPIService {

    private final SlackBotProperties slackBotProperties;

    public void postMessage(String channel, String text) throws IOException, SlackApiException {
        final Slack slack = Slack.getInstance();
        slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                                                              .token(slackBotProperties.getBot().getToken())
                                                              .channel(channel)
                                                              .text(text)
                                                              .build());
    }

    public UsersInfoResponse getUsersInfo(String user) throws IOException, SlackApiException {
        final Slack slack = Slack.getInstance();
        return slack.methods().usersInfo(UsersInfoRequest.builder()
                                                         .token(slackBotProperties.getBot().getToken())
                                                         .user(user)
                                                         .build());
    }
}
