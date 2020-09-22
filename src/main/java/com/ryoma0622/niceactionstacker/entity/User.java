package com.ryoma0622.niceactionstacker.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private int id;
    private String slackId;
    private String name;
    private String channel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
