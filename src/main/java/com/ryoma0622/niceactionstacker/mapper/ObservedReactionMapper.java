package com.ryoma0622.niceactionstacker.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ryoma0622.niceactionstacker.entity.ObservedReaction;

@Mapper
@Repository
public interface ObservedReactionMapper {

    int insert(ObservedReaction observedReaction);
    List<ObservedReaction> selectBySlackId(String slackId);
    int deleteById(int id);
}
