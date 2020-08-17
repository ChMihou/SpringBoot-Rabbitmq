package com.space.rabbitmq.controller;

import com.space.rabbitmq.sender.FirstSender;
import com.space.rabbitmq.sender.SecondSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class SendController {

    @Autowired
    private FirstSender firstSender;

    @Autowired
    private SecondSender secondSender;

    @GetMapping("/send")
    public String send(String message){
        String uuid = UUID.randomUUID().toString();
        firstSender.send(uuid,message);
        return uuid;
    }
    @GetMapping("/send2")
    public String send2(String message){
        String uuid = UUID.randomUUID().toString();
        secondSender.send(uuid,message);
        return uuid;
    }
}
