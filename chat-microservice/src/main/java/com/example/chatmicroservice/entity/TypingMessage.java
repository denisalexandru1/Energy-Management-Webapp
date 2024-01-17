package com.example.chatmicroservice.entity;

import java.util.UUID;

public class TypingMessage {
    public UUID senderId;
    public UUID receiverId;
    public Boolean isTyping;
}
