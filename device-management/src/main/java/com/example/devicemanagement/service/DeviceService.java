package com.example.devicemanagement.service;

import com.example.devicemanagement.dto.DeviceDTO;
import com.example.devicemanagement.dto.DeviceRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface DeviceService {
    DeviceDTO registerDevice(DeviceRegisterDTO dto);

    List<DeviceDTO> getAllDevices();

    DeviceDTO updateDevice(UUID uuid, DeviceDTO dto);

    void deleteDevice(UUID uuid);

    List<DeviceDTO> getAllDevicesByUserId(UUID uuid);

    void deleteAllDevicesByUserId(UUID uuid);
}
