package com.riwi.Authentication.controllers.interfaces;

import com.riwi.Authentication.controllers.generics.Byid;
import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.controllers.generics.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface InterfaceUserController extends
        Byid<Long, UserResponse>,
        Create<UserRequest,String>,
        Delete<String,Long>,
        ReadAll<UserResponse>,
        Put<UserRequest, Long>,
        Path<UserRequest, Long>{
/*
    @PostMapping
    ResponseEntity<String> create(@RequestBody UserRequest entity);

    @PutMapping("/update/{id}")
    ResponseEntity<String> update(UserRequest userRequest, Long id);
 */

}
