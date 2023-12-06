package com.example.monitoringmicroservice.service.impl;

import com.example.monitoringmicroservice.dto.DeviceDTO;
import com.example.monitoringmicroservice.entity.Device;
import com.example.monitoringmicroservice.mapper.DeviceMapper;
import com.example.monitoringmicroservice.repository.DeviceRepository;
import com.example.monitoringmicroservice.service.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }
    @Override
    public DeviceDTO registerDevice(String message) {
        String trimmedJson = message.replaceAll("[{}\"]", "");
        String[] keyValuePairs = trimmedJson.split(",");
        String uuid = keyValuePairs[1].split(":")[1].trim();
        String userUuid = keyValuePairs[2].split(":")[1].trim();
        String maxHourlyConsumption = keyValuePairs[3].split(":")[1].trim();
        DeviceDTO deviceDTO = deviceMapper.toDTO(uuid, userUuid, maxHourlyConsumption);
        Device savedDevice = deviceRepository.save(deviceMapper.toDevice(deviceDTO));
        return deviceMapper.toDTO(savedDevice);
    }

    @Override
    public DeviceDTO updateDevice(String message) {
        String trimmedJson = message.replaceAll("[{}\"]", "");
        String[] keyValuePairs = trimmedJson.split(",");
        String uuid = keyValuePairs[1].split(":")[1].trim();
        String userUuid = keyValuePairs[2].split(":")[1].trim();
        String maxHourlyConsumption = keyValuePairs[3].split(":")[1].trim();
        DeviceDTO deviceDTO = deviceMapper.toDTO(uuid, userUuid, maxHourlyConsumption);
        Device updatedDevice = deviceRepository.save(deviceMapper.toDevice(deviceDTO));
        return deviceMapper.toDTO(updatedDevice);
    }

    @Override
    public void deleteDevice(String message) {
        String trimmedJson = message.replaceAll("[{}\"]", "");
        String[] keyValuePairs = trimmedJson.split(",");
        String uuid = keyValuePairs[1].split(":")[1].trim();
        deviceRepository.deleteById(UUID.fromString(uuid));
    }

    @Override
    public List<DeviceDTO> getAllDevicesForUser(UUID uuid) {
        List<Device> devices = deviceRepository.findAllByUserUuid(uuid);
        return devices.stream().map(deviceMapper::toDTO).collect(java.util.stream.Collectors.toList());
    }


}
