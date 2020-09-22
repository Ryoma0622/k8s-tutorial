package com.ryoma0622.niceactionstacker.handler.slackrtm;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.ReactionAddedEvent;
import com.slack.api.rtm.RTMEventHandler;

import com.ryoma0622.niceactionstacker.controller.ObservedReactionController;
import com.ryoma0622.niceactionstacker.controller.StackedMessageController;
import com.ryoma0622.niceactionstacker.controller.UserController;
import com.ryoma0622.niceactionstacker.entity.ObservedReaction;
import com.ryoma0622.niceactionstacker.entity.StackedMessage;
import com.ryoma0622.niceactionstacker.entity.User;
import com.ryoma0622.niceactionstacker.properties.SlackBotProperties;
import com.ryoma0622.niceactionstacker.service.SlackAPIService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReactionAddedEventHandler extends RTMEventHandler<ReactionAddedEvent> {

    private final SlackBotProperties slackBotProperties;
    private final UserController userController;
    private final ObservedReactionController observedReactionController;
    private final StackedMessageController stackedMessageController;
    private final SlackAPIService slackAPIService;

    @SneakyThrows
    @Override
    public void handle(ReactionAddedEvent event) {
        final User itemUser = userController.getBySlackId(event.getItemUser());
        final User reactionUser = userController.getBySlackId(event.getUser());

        if (itemUser != null) {
            reactionHandleProcess(event, itemUser, "Received");
        }

        if (reactionUser != null) {
            reactionHandleProcess(event, reactionUser, "Reacting");
        }
    }


    private void reactionHandleProcess(ReactionAddedEvent event, User targetUser, String actionName)
            throws IOException, SlackApiException {
        final List<ObservedReaction> observedReactions = observedReactionController.getBySlackId(targetUser.getSlackId());
        final boolean isReactionMatch = observedReactions.stream().anyMatch(r -> r.getName().equals(event.getReaction()));
        if (!isReactionMatch) {
            return;
        }
        final String messagePath = String.format("%s/p%s", event.getItem().getChannel(), event.getItem().getTs().replace(".", ""));
        final StackedMessage stackedMessage = stackedMessageController.getBySlackIdAndPath(targetUser.getSlackId(), messagePath);
        if (!ObjectUtils.isEmpty(stackedMessage)) {
            return;
        }
        final String itemUrl = slackBotProperties.getBaseUrl() + messagePath;
        slackAPIService.postMessage(targetUser.getChannel(), String.format("%s: [Reaction] :%s: [Message's URL]: %s",
                                                                           actionName,
                                                                           event.getReaction(),
                                                                           itemUrl));
        stackedMessageController.create(StackedMessage.builder().slackId(targetUser.getSlackId()).path(messagePath).build());
    }

}
