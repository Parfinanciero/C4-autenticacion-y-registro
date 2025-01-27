package com.riwi.Authentication.controllers.interfaces;

import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface InterfaceUserController {

    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest);

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers();

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequestDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id);
}
