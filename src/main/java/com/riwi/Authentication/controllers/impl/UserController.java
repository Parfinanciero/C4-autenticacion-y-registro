package com.riwi.Authentication.controllers.impl;

import com.riwi.Authentication.controllers.interfaces.InterfaceUserController;
import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.servicies.interfaces.IUService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController implements InterfaceUserController {

    @Autowired
    IUService iuService;


    @PostMapping
    @Override
    public ResponseEntity<String> create(@RequestBody @Valid UserRequest entity) {
        iuService.create(entity);
        return ResponseEntity.ok("User successfully created");
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> ById( @PathVariable  Long id) {
        UserResponse response = iuService.readById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        iuService.delete(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    @Override
    @PatchMapping("/path/{id}")
    public ResponseEntity<String> path(@RequestBody @Valid UserRequest userRequest,@PathVariable Long id) {
        iuService.path(userRequest, id);
        return ResponseEntity.ok("User successfully update");
    }

    @PutMapping("/update/{id}")
    @Override
    public ResponseEntity<String> update(@RequestBody @Valid UserRequest userRequest,@PathVariable Long id) {
            iuService.update(userRequest, id);
            return ResponseEntity.ok("user successfully updated");
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserResponse>> readAll() {
        return ResponseEntity.ok(iuService.readAll());
    }

}
