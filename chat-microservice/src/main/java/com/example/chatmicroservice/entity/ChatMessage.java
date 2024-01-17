package com.example.chatmicroservice.entity;

import java.util.UUID;

public class ChatMessage {
    public UUID senderId;
    public UUID receiverId;
    public String message;
}
