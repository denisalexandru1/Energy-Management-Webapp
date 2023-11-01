package com.example.devicemanagement.service;

import com.example.devicemanagement.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service

public interface UserService {
    UserDTO registerUser(UserDTO dto);

    UserDTO updateUser(UUID uuid, UserDTO dto);

    void deleteUser(UUID uuid);

    UserDTO getUserById(UUID userUuid);
}
