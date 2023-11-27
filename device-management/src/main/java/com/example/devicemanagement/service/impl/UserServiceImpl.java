package com.example.devicemanagement.service.impl;

import com.example.devicemanagement.dto.UserDTO;
import com.example.devicemanagement.entity.User;
import com.example.devicemanagement.mapper.UserMapper;
import com.example.devicemanagement.repository.UserRepository;
import com.example.devicemanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(UserDTO dto) {
        if(userRepository.findByUsername(dto.username) != null){
            throw new InvalidParameterException("Username already exists");
        }
        User savedUser = userRepository.save(userMapper.toUser(dto));
        return userMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(UUID userUuid) {
        Optional<User> user = userRepository.findById(userUuid);
        if(user.isPresent()){
            return userMapper.toDTO(user.get());
        }
        else{
            throw new InvalidParameterException("There is no user with uuid " + userUuid);
        }
    }

    @Override
    public UserDTO updateUser(UUID uuid, UserDTO dto) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            User savedUser = user.get();
            savedUser.setUsername(dto.username);
            userRepository.save(savedUser);
            return userMapper.toDTO(savedUser);
        } else {
            throw new InvalidParameterException("There is no user with uuid " + uuid);
        }
    }

    @Override
    public void deleteUser(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new InvalidParameterException("There is no user with uuid " + uuid);
        }
    }
}
