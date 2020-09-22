package com.ryoma0622.niceactionstacker.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ryoma0622.niceactionstacker.entity.ObservedReaction;
import com.ryoma0622.niceactionstacker.entity.StackedMessage;

@Mapper
@Repository
public interface StackedMessageMapper {

    int insert(StackedMessage stackedMessage);
    StackedMessage selectBySlackIdAndPath(String slackId, String path);
}
