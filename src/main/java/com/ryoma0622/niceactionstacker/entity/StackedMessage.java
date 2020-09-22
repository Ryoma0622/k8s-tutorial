package com.ryoma0622.niceactionstacker.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StackedMessage {

    private int id;
    private String slackId;
    private String path;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
