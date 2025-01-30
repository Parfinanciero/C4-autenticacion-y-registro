package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReadAll<EntityResponse> {
    public ResponseEntity<List<EntityResponse>> readAll();
}
