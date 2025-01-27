package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Create<EntityRequest,Entity> {

    public ResponseEntity<Entity> create(EntityRequest entity);

}
