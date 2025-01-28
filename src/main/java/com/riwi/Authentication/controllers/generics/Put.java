package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Put<EntityRequest,ID> {
    public ResponseEntity<String> update(EntityRequest entity, ID id);
}
