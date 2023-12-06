package com.example.monitoringmicroservice.service;

import com.example.monitoringmicroservice.dto.DeviceDTO;

import java.util.List;
import java.util.UUID;

public interface DeviceService {

    DeviceDTO registerDevice(String message);

    DeviceDTO updateDevice(String message);

    void deleteDevice(String message);

    List<DeviceDTO> getAllDevicesForUser(UUID uuid);
}
