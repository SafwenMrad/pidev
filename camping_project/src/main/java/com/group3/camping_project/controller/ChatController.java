package com.group3.camping_project.controller;

import com.group3.camping_project.service.Chatservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    Chatservice chatservice;
    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
       return chatservice.chat(message);
    }

}
