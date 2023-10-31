package com.example.usermanagement.controller;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserLoginDTO;
import com.example.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class LogInController {
    private final UserService userService;

    public LogInController(UserService userService){
        this.userService = userService;
    }

    @PostMapping ("/login")
    ResponseEntity<?> login(@RequestBody UserLoginDTO dto){
        try{
            UserDTO user = userService.getUserByUsername(dto.username);
            if(user.password.equals(dto.password)){
                return ResponseEntity.ok(user);
            }
            else{
                return ResponseEntity.badRequest().body("Wrong password");
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}
