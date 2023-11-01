package com.example.usermanagement.controller;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.dto.UserRegisterDTO;
import com.example.usermanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping ("/user")
    ResponseEntity<UserDTO> createUser(@RequestBody UserRegisterDTO dto){
        UserDTO registeredUser = userService.registerUser(dto);

        WebClient client = WebClient.create("http://172.30.0.5:8081");
        ResponseEntity<?> responseEntity = client.post()
                .uri("/user")
                .bodyValue(registeredUser)
                .retrieve()
                .toEntity(Void.class)
                .block();

        if(responseEntity.getStatusCode().isError()){
            throw new RuntimeException("Error while registering user to device management service");
        }
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/user")
    ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/user/{id}")
    ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID uuid, @RequestBody UserDTO dto){
        UserDTO updatedUser = userService.updateUser(uuid, dto);

        WebClient client = WebClient.create("http://172.30.0.5:8081");
        ResponseEntity<?> responseEntity = client.put()
                .uri("/user/{id}", uuid)
                .bodyValue(updatedUser)
                .retrieve()
                .toEntity(Void.class)
                .block();

        if(responseEntity.getStatusCode().isError()){
            throw new RuntimeException("Error while updating user in device management service");
        }
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") UUID uuid){
        userService.deleteUser(uuid);

        WebClient client = WebClient.create("http://172.30.0.5:8081");
        ResponseEntity<?> responseEntity = client.delete()
                .uri("/user/{id}", uuid)
                .retrieve()
                .toEntity(Void.class)
                .block();

        if(responseEntity.getStatusCode().isError()){
            throw new RuntimeException("Error while deleting user from device management service");
        }
        return ResponseEntity.ok().build();
    }
}
