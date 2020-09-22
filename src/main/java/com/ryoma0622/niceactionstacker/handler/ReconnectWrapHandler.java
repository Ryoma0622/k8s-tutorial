package com.ryoma0622.niceactionstacker.handler;

import javax.websocket.CloseReason;

import org.glassfish.tyrus.client.ClientManager;
import org.springframework.stereotype.Component;

import com.ryoma0622.niceactionstacker.service.NiceActionStackerService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReconnectWrapHandler extends ClientManager.ReconnectHandler{

    private final NiceActionStackerService niceActionStackerService;

    private int counter;
    private int reconnectionFailedCounter;

    @SneakyThrows
    @Override
    public boolean onDisconnect(CloseReason closeReason) {
        log.info("########## onDisconnect ##########");
        reconnectionFailedCounter = 0;
        counter++;
        log.info("### Reconnecting... (reconnection count: " + counter + ')');
        niceActionStackerService.exec();
        return true;
    }

    @SneakyThrows
    @Override
    public boolean onConnectFailure(Exception exception) {
        log.info("########## onConnectFailure ##########");
        counter++;
        reconnectionFailedCounter++;
        log.error("### Connection Failed: count: " + reconnectionFailedCounter, exception);
        if (reconnectionFailedCounter > 3) {
            log.error("### Reconnection is failed.");
            return false;
        }
        log.info("### Reconnecting... (reconnection count: " + counter + ')');
        niceActionStackerService.exec();
        return true;
    }
}
