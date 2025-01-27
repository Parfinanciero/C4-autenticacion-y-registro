package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Path  <EntityRequest,ID>{
    public ResponseEntity<String> path(EntityRequest request, ID id);
}
