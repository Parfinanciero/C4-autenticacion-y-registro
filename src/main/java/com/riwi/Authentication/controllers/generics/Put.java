package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Put<EntityRequest,ID> {
    public ResponseEntity<String> put(EntityRequest entity, ID id);
}
