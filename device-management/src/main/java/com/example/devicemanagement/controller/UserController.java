package com.example.devicemanagement.controller;

import com.example.devicemanagement.dto.UserDTO;
import com.example.devicemanagement.service.DeviceService;
import com.example.devicemanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private final DeviceService deviceService;

    public UserController(UserService userService, DeviceService deviceService){
        this.userService = userService;
        this.deviceService = deviceService;
    }

    @PostMapping("/user")
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto){
        UserDTO registeredUser = userService.registerUser(dto);
        return ResponseEntity.ok(registeredUser);
    }

    @PutMapping("/user/{id}")
    ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID uuid, @RequestBody UserDTO dto){
        UserDTO updatedUser = userService.updateUser(uuid, dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") UUID uuid){
        userService.deleteUser(uuid);
        deviceService.deleteAllDevicesByUserId(uuid);
        return ResponseEntity.ok().build();
    }
}
