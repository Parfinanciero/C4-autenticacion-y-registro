package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Create<EntityRequest, EntityResponse> {
    ResponseEntity<EntityResponse> create(EntityRequest request);
}
