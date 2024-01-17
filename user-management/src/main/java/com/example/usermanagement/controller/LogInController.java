package com.example.usermanagement.controller;

import com.example.usermanagement.authorization.TokenGenerator;
import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserLoginDTO;
import com.example.usermanagement.service.UserService;
import org.apache.el.parser.Token;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class LogInController {
    private final UserService userService;
    private final TokenGenerator tokenGenerator = new TokenGenerator();
    public LogInController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserLoginDTO dto){
        try{
            UserDTO user = userService.getUserByUsername(dto.username);
            System.out.println(user.username + " " + user.password);
            if(user != null && user.password.equals(dto.password))
            {
                String token = tokenGenerator.generateJWTToken(user.username);

                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("user", user);

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Wrong username or password");
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}
