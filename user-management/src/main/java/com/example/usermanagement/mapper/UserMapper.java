package com.example.usermanagement.mapper;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserRegisterDTO;
import com.example.usermanagement.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserRegisterDTO dto){
        User user = new User();
        user.username = dto.username;
        user.password = dto.password;
        user.isAdmin = dto.isAdmin;
        return user;
    }

    public UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.uuid = user.uuid;
        dto.username = user.username;
        dto.password = user.password;
        dto.isAdmin = user.isAdmin;
        return dto;
    }
}
