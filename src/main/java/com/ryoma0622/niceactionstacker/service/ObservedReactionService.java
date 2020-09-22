package com.ryoma0622.niceactionstacker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ryoma0622.niceactionstacker.entity.ObservedReaction;
import com.ryoma0622.niceactionstacker.mapper.ObservedReactionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ObservedReactionService {

    private final ObservedReactionMapper observedReactionMapper;

    public int save(ObservedReaction observedReaction) {
        return observedReactionMapper.insert(observedReaction);
    }

    public List<ObservedReaction> findBySlackId(String slackId) {
        return observedReactionMapper.selectBySlackId(slackId);
    }

    public int delete(int id) {
        return observedReactionMapper.deleteById(id);
    }
}
