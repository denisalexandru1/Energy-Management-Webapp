package com.example.usermanagement.service.impl;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserRegisterDTO;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(UserRegisterDTO dto) {
        if(userRepository.findByUsername(dto.username) != null){
            throw new InvalidParameterException("Username already exists");
        }
        User savedUser = userRepository.save(userMapper.toUser(dto));
        return userMapper.toDTO(savedUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(toList());
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return userMapper.toDTO(user);
        }
        else{
            throw new InvalidParameterException("There is no user with username " + username);
        }
    }

    @Override
    public UserDTO updateUser(UUID uuid, UserDTO dto) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            User savedUser = user.get();
            savedUser.setUsername(dto.username);
            savedUser.setPassword(dto.password);
            savedUser.setIsAdmin(dto.isAdmin);
            userRepository.save(savedUser);
            return userMapper.toDTO(savedUser);
        } else {
            throw new InvalidParameterException("There is no user with id" + uuid);
        }
    }

    @Override
    public void deleteUser(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new InvalidParameterException("There is no user with id" + uuid);
        }
    }

    @Override
    public List<UserDTO> getAllAdmins() {
        List<User> users = userRepository.findAllByIsAdmin(true);
        return users.stream().map(userMapper::toDTO).collect(toList());
    }

    @Override
    public UserDTO getUserById(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            return userMapper.toDTO(user.get());
        } else {
            throw new InvalidParameterException("There is no user with id" + uuid);
        }
    }
}
