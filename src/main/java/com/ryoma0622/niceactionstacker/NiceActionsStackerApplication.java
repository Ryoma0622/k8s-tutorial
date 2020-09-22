package com.ryoma0622.niceactionstacker;

import java.io.IOException;

import javax.websocket.DeploymentException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ryoma0622.niceactionstacker.service.NiceActionStackerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class NiceActionsStackerApplication implements CommandLineRunner {

    private final NiceActionStackerService niceActionStackerService;

    public static void main(String... args) {
        SpringApplication.run(NiceActionsStackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException, DeploymentException, InterruptedException {
        niceActionStackerService.exec();
    }
}
