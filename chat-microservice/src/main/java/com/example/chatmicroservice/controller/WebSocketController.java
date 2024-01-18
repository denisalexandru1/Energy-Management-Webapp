package com.example.chatmicroservice.controller;

import com.example.chatmicroservice.entity.ChatMessage;
import com.example.chatmicroservice.entity.TypingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3001")
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/publish")
    public void handleChatMessage(@Payload ChatMessage message) {

        System.out.println("Received message: " + message.message + " from " + message.senderId + " to " + message.receiverId);

        messagingTemplate.convertAndSend("/topic/chat", message);
    }

    @MessageMapping("/typing")
    public void handleTypingEvent(@Payload TypingMessage message) {

        System.out.println("Received typing event: " + message.senderId + " to " + message.receiverId);

        messagingTemplate.convertAndSend("/topic/typing", message);
    }
}
