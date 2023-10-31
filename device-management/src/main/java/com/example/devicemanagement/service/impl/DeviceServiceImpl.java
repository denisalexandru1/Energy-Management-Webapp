package com.example.devicemanagement.service.impl;

import com.example.devicemanagement.dto.DeviceDTO;
import com.example.devicemanagement.dto.DeviceRegisterDTO;
import com.example.devicemanagement.entity.Device;
import com.example.devicemanagement.mapper.DeviceMapper;
import com.example.devicemanagement.repository.DeviceRepository;
import com.example.devicemanagement.service.DeviceService;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
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
    public DeviceDTO registerDevice(DeviceRegisterDTO dto) {
        Device savedDevice = deviceRepository.save(deviceMapper.toDevice(dto));
        return deviceMapper.toDTO(savedDevice);
    }

    @Override
    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(deviceMapper::toDTO).toList();
    }

    @Override
    public List<DeviceDTO> getAllDevicesByUserId(UUID uuid) {
        List<Device> devices = deviceRepository.findAllByUserUuid(uuid);
        return devices.stream().map(deviceMapper::toDTO).toList();
    }

    @Override
    public DeviceDTO updateDevice(UUID uuid, DeviceDTO dto) {
        Optional<Device> device = deviceRepository.findById(uuid);
        if(device.isPresent()){
            Device savedDevice = device.get();
            savedDevice.userUuid = dto.userUuid;
            savedDevice.description = dto.description;
            savedDevice.addres = dto.address;
            savedDevice.maxHourlyConsumption = dto.maxHourlyConsumption;
            return deviceMapper.toDTO(deviceRepository.save(savedDevice));
        }
        else{
            throw new InvalidParameterException("There is no device with uuid" + dto.uuid);
        }
    }

    @Override
    public void deleteDevice(UUID uuid) {
        Optional<Device> device = deviceRepository.findById(uuid);
        if(device.isPresent()){
            deviceRepository.delete(device.get());
        }
        else{
            throw new InvalidParameterException("There is no device with uuid" + uuid);
        }
    }

    @Override
    public void deleteAllDevicesByUserId(UUID uuid) {
        List<Device> devices = deviceRepository.findAllByUserUuid(uuid);
        if(devices.size() > 0){
            deviceRepository.deleteAll(devices);
        }
    }
}
