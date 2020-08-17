package com.space.rabbitmq.controller;

import com.space.rabbitmq.enity.Message;
import com.space.rabbitmq.sender.FirstSender;
import com.space.rabbitmq.sender.SecondSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;


@RestController
public class SendController {

    @Autowired
    private FirstSender firstSender;

    @Autowired
    private SecondSender secondSender;

    @GetMapping("/send")
    public String send(){
        Message message = new Message();
        String uuid = UUID.randomUUID().toString();
        message.setId(uuid);
        message.setMessage("你好吗，渠道一");
        message.setSendTime(new Date());
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
