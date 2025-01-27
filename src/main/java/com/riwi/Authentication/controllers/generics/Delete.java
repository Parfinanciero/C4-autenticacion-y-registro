package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Delete<Entity,ID> {

    public ResponseEntity<Entity> delete(ID id);
}
