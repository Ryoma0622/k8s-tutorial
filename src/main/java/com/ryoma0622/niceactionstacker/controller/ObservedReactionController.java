package com.ryoma0622.niceactionstacker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ryoma0622.niceactionstacker.entity.ObservedReaction;
import com.ryoma0622.niceactionstacker.service.ObservedReactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/observed_reactions")
@Slf4j
@RequiredArgsConstructor
public class ObservedReactionController {

    private final ObservedReactionService observedReactionService;

    @PostMapping
    public int create(@RequestBody ObservedReaction observedReaction) {
        return observedReactionService.save(observedReaction);
    }

    @GetMapping
    public List<ObservedReaction> getBySlackId(@RequestParam("slackId") String slackId) {
        return observedReactionService.findBySlackId(slackId);
    }

    @DeleteMapping("/{id}")
    public int deleteById(@PathVariable int id) {
        return observedReactionService.delete(id);
    }
}
