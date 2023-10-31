package com.example.devicemanagement.mapper;

import com.example.devicemanagement.dto.UserDTO;
import com.example.devicemanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserDTO dto) {
        User user = new User();
        user.uuid = dto.uuid;
        user.username = dto.username;
        return user;
    }

    public UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.uuid = user.uuid;
        dto.username = user.username;
        return dto;
    }
}
