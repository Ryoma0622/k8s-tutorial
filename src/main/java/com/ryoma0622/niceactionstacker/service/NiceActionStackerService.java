package com.ryoma0622.niceactionstacker.service;

import java.io.IOException;

import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slack.api.Slack;
import com.slack.api.rtm.RTMClient;
import com.slack.api.rtm.RTMEventsDispatcher;
import com.slack.api.rtm.RTMEventsDispatcherFactory;

import com.ryoma0622.niceactionstacker.handler.ReconnectWrapHandler;
import com.ryoma0622.niceactionstacker.handler.slackrtm.MessageEventHandler;
import com.ryoma0622.niceactionstacker.handler.slackrtm.ReactionAddedEventHandler;
import com.ryoma0622.niceactionstacker.properties.SlackBotProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NiceActionStackerService {

    private final SlackBotProperties slackBotProperties;
    private final ReactionAddedEventHandler reactionAddedEventHandler;
    private final MessageEventHandler messageEventHandler;

    public void exec() throws IOException, InterruptedException, DeploymentException {
        final Slack slack = Slack.getInstance();
        final RTMClient rtm = slack.rtmConnect(slackBotProperties.getBot().getToken());

        final RTMEventsDispatcher dispatcher = RTMEventsDispatcherFactory.getInstance();
        dispatcher.register(reactionAddedEventHandler);
        dispatcher.register(messageEventHandler);
        rtm.addMessageHandler(dispatcher.toMessageHandler());

        connect(slack, rtm);
    }

    /**
     * Refs:
     *  - https://api.slack.com/events/goodbye
     *  - https://tyrus-project.github.io/documentation/1.13.1/user-guide.html#d0e1311
     *  - https://github.com/slackapi/java-slack-sdk/issues/464
     */
    public void connect(Slack slack, RTMClient rtmClient)
            throws IOException, DeploymentException, InterruptedException {
        final ClientManager client = ClientManager.createClient();

        client.getProperties().put(ClientProperties.RECONNECT_HANDLER, new ReconnectWrapHandler(this));

        final String proxy = slack.getHttpClient().getConfig().getProxyUrl();
        if (proxy != null) {
            log.debug("The RTM client's going to use an HTTP proxy: {}", proxy);
            client.getProperties().put(ClientProperties.PROXY_URI, proxy);
        }
        client.connectToServer(rtmClient, rtmClient.getWssUri());
        log.info("client connected to the server: {}", rtmClient.getWssUri());

        while (true) {
            Thread.sleep(Long.MAX_VALUE);
        }
    }
}
