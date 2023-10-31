package com.example.devicemanagement.controller;

import com.example.devicemanagement.dto.DeviceRegisterDTO;
import com.example.devicemanagement.dto.DeviceDTO;
import com.example.devicemanagement.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService){
        this.deviceService = deviceService;
    }

    @PostMapping("/device")
    ResponseEntity<DeviceDTO> registerDevice(@RequestBody DeviceRegisterDTO dto){
        DeviceDTO registeredDevice = deviceService.registerDevice(dto);
        return ResponseEntity.ok(registeredDevice);
    }

    @GetMapping("/device")
    ResponseEntity<List<DeviceDTO>> getAllDevices(){
        List<DeviceDTO> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/device/user/{id}")
    ResponseEntity<List<DeviceDTO>> getAllDevicesByUserId(@PathVariable("id") UUID uuid){
        List<DeviceDTO> devices = deviceService.getAllDevicesByUserId(uuid);
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/device/{id}")
    ResponseEntity<DeviceDTO> updateDevice(@PathVariable("id") UUID uuid, @RequestBody DeviceDTO dto){
        DeviceDTO updatedDevice = deviceService.updateDevice(uuid, dto);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/device/{id}")
    ResponseEntity<DeviceDTO> deleteDevice(@PathVariable("id") UUID uuid){
        deviceService.deleteDevice(uuid);
        return ResponseEntity.ok().build();
    }
}
