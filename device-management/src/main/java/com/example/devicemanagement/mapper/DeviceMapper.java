package com.example.devicemanagement.mapper;

import com.example.devicemanagement.dto.DeviceDTO;
import com.example.devicemanagement.dto.DeviceRegisterDTO;
import com.example.devicemanagement.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public Device toDevice(DeviceRegisterDTO dto){
        Device device = new Device();
        device.userUuid = dto.userUuid;
        device.description = dto.description;
        device.addres = dto.address;
        device.maxHourlyConsumption = dto.maxHourlyConsumption;
        return device;
    }

    public DeviceDTO toDTO(Device device) {
        DeviceDTO dto = new DeviceDTO();
        dto.uuid = device.uuid;
        dto.userUuid = device.userUuid;
        dto.description = device.description;
        dto.address = device.addres;
        dto.maxHourlyConsumption = device.maxHourlyConsumption;
        return dto;
    }
}
