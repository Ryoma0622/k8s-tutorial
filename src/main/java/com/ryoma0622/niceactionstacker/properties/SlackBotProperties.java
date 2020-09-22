package com.ryoma0622.niceactionstacker.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "slack")
@Getter
@Setter
public class SlackBotProperties {
    private String baseUrl;
    private Bot bot;

    @Getter
    @Setter
    public static class Bot {
        private String token;
        private String id;
    }
}
