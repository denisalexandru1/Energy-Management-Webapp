package com.example.devicemanagement.service;

import com.example.devicemanagement.dto.DeviceDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface QueueService {
    void SendPostRequest(DeviceDTO dto);
    void SendPutRequest(DeviceDTO dto);
    void SendDeleteRequest(UUID uuid);
}
