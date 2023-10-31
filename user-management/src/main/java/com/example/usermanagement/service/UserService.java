package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {
    UserDTO registerUser(UserRegisterDTO dto);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UUID uuid, UserDTO dto);

    UserDTO getUserByUsername(String username);

    void deleteUser(UUID uuid);
}
