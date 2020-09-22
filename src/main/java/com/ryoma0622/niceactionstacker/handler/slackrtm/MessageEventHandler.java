package com.ryoma0622.niceactionstacker.handler.slackrtm;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.slack.api.methods.response.users.UsersInfoResponse;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.rtm.RTMEventHandler;

import com.ryoma0622.niceactionstacker.config.MentionProcessType;
import com.ryoma0622.niceactionstacker.controller.ObservedReactionController;
import com.ryoma0622.niceactionstacker.controller.UserController;
import com.ryoma0622.niceactionstacker.entity.ObservedReaction;
import com.ryoma0622.niceactionstacker.entity.User;
import com.ryoma0622.niceactionstacker.properties.SlackBotProperties;
import com.ryoma0622.niceactionstacker.service.SlackAPIService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageEventHandler extends RTMEventHandler<MessageEvent> {

    private final SlackBotProperties slackBotProperties;
    private final UserController userController;
    private final ObservedReactionController observedReactionController;
    private final SlackAPIService slackAPIService;

    @SneakyThrows
    @Override
    public void handle(MessageEvent event) {
        if (!event.getText().startsWith(String.format("<%s>", slackBotProperties.getBot().getId()))) {
            return;
        }

        final List<String> messages = Arrays.asList(event.getText().split(" "));
        if (messages.size() < 2) {
            slackAPIService.postMessage(event.getChannel(), "Invalid Command");
            return;

        }
        final MentionProcessType processType = MentionProcessType.of(messages.get(1));
        if (processType == null) {
            slackAPIService.postMessage(event.getChannel(), "Invalid Command");
            return;
        }

        final User user = userController.getBySlackId(event.getUser());
        if (!ObjectUtils.isEmpty(user)) {
            slackAPIService.postMessage(event.getChannel(), "You already have started up.");
            return;
        }

        final UsersInfoResponse usersInfo = slackAPIService.getUsersInfo(event.getUser());
        final List<String> arguments = messages.stream().skip(2).collect(Collectors.toList());
        switch (processType) {
            case STARTUP -> {
                if (arguments.size() != 1) {
                    slackAPIService.postMessage(event.getChannel(), "Invalid Command");
                    return;
                }

                final String receivedChannel = arguments.get(0);
                if (!receivedChannel.startsWith("C")) {
                    slackAPIService.postMessage(event.getChannel(), "Invalid Command");
                    return;
                }

                userController.create(User.builder()
                                          .slackId(usersInfo.getUser().getId())
                                          .name(usersInfo.getUser().getName())
                                          .channel(receivedChannel)
                                          .build());
                slackAPIService.postMessage(event.getChannel(),
                                            String.format("Start observing: [User] %s [Received Channel] %s",
                                                          usersInfo.getUser().getName(),
                                                          receivedChannel));
            }
            case SET_REACTION -> {
                if (arguments.size() != 1) {
                    slackAPIService.postMessage(event.getChannel(), "Invalid Command");
                    return;
                }
                final String reactionName = arguments.get(0);
                if (!(reactionName.startsWith(":") && reactionName.endsWith(":"))) {
                    slackAPIService.postMessage(event.getChannel(), "Invalid Command");
                    return;
                }
                final List<ObservedReaction> observedReactions = observedReactionController.getBySlackId(event.getUser());
                final Optional<ObservedReaction> targetReaction = observedReactions
                        .stream()
                        .filter(r -> r.getName().equals(reactionName.replace(":", "")))
                        .findFirst();
                if (targetReaction.isPresent()) {
                    slackAPIService.postMessage(event.getChannel(), "This Reaction already exist.");
                    return;
                }
                observedReactionController.create(ObservedReaction.builder()
                                                                  .slackId(usersInfo.getUser().getId())
                                                                  .name(reactionName.replace(":", ""))
                                                                  .build());
                slackAPIService.postMessage(event.getChannel(),
                                            String.format("Set reaction: [User] %s [Reaction] %s",
                                                          usersInfo.getUser().getName(),
                                                          reactionName));
            }
            case REMOVE_REACTION -> {
                if (arguments.size() != 1) {
                    slackAPIService.postMessage(event.getChannel(), "Invalid Command");
                    return;
                }
                final String reactionName = arguments.get(0);
                if (!(reactionName.startsWith(":") && reactionName.endsWith(":"))) {
                    slackAPIService.postMessage(event.getChannel(), "Invalid Command");
                    return;
                }
                final List<ObservedReaction> observedReactions = observedReactionController.getBySlackId(event.getUser());
                final Optional<ObservedReaction> targetReaction = observedReactions
                        .stream()
                        .filter(r -> r.getName().equals(reactionName.replace(":", "")))
                        .findFirst();
                if (targetReaction.isEmpty()) {
                    slackAPIService.postMessage(event.getChannel(), "This Reaction doesn't exist.");
                    return;
                }
                observedReactionController.deleteById(targetReaction.get().getId());
                slackAPIService.postMessage(event.getChannel(),
                                            String.format("Remove reaction: [User] %s [Reaction] %s",
                                                          usersInfo.getUser().getName(),
                                                          reactionName));
            }
        }
    }
}
