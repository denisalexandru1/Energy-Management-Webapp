package com.example.devicemanagement.controller;

import com.example.devicemanagement.authorization.TokenValidator;
import com.example.devicemanagement.dto.DeviceRegisterDTO;
import com.example.devicemanagement.dto.DeviceDTO;
import com.example.devicemanagement.dto.UserDTO;
import com.example.devicemanagement.service.DeviceService;
import com.example.devicemanagement.service.QueueService;
import com.example.devicemanagement.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class DeviceController {
    private final DeviceService deviceService;
    private final UserService userService;
    private final QueueService queueService;
    private final TokenValidator tokenValidator;

    public DeviceController(DeviceService deviceService, UserService userService, QueueService queueService){
        this.deviceService = deviceService;
        this.userService = userService;
        this.queueService = queueService;
        this.tokenValidator = new TokenValidator();
    }

    @PostMapping("/device")
    ResponseEntity<DeviceDTO> registerDevice(@RequestBody DeviceRegisterDTO dto, @RequestHeader (HttpHeaders.AUTHORIZATION) String token){
        if (tokenValidator.validate(token) == false){
            return ResponseEntity.badRequest().build();
        }
        DeviceDTO registeredDevice = deviceService.registerDevice(dto);
        queueService.SendPostRequest(registeredDevice);
        return ResponseEntity.ok(registeredDevice);
    }

    @GetMapping("/device")
    ResponseEntity<List<DeviceDTO>> getAllDevices(@RequestHeader (HttpHeaders.AUTHORIZATION) String token){
        if (tokenValidator.validate(token) == false){
            return ResponseEntity.badRequest().build();
        }

        List<DeviceDTO> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/device/user/{id}")
    ResponseEntity<List<DeviceDTO>> getAllDevicesByUserId(@PathVariable("id") UUID uuid, @RequestHeader (HttpHeaders.AUTHORIZATION) String token){
        if (tokenValidator.validate(token) == false){
            return ResponseEntity.badRequest().build();
        }
        List<DeviceDTO> devices = deviceService.getAllDevicesByUserId(uuid);
        return ResponseEntity.ok(devices);
    }

    @PutMapping("/device/{id}")
    ResponseEntity<DeviceDTO> updateDevice(@PathVariable("id") UUID uuid, @RequestBody DeviceDTO dto, @RequestHeader (HttpHeaders.AUTHORIZATION) String token){
        if (tokenValidator.validate(token) == false){
            return ResponseEntity.badRequest().build();
        }

        UserDTO user = userService.getUserById(dto.userUuid);
        DeviceDTO updatedDevice = deviceService.updateDevice(uuid, dto);
        queueService.SendPutRequest(updatedDevice);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/device/{id}")
    ResponseEntity<DeviceDTO> deleteDevice(@PathVariable("id") UUID uuid, @RequestHeader (HttpHeaders.AUTHORIZATION) String token){
        if (tokenValidator.validate(token) == false){
            return ResponseEntity.badRequest().build();
        }
        deviceService.deleteDevice(uuid);
        queueService.SendDeleteRequest(uuid);
        return ResponseEntity.ok().build();
    }
}
