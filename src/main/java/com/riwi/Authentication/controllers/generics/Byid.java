package com.riwi.Authentication.controllers.generics;

import org.springframework.http.ResponseEntity;

public interface Byid<ID, EntityResponse>  {

    public ResponseEntity<EntityResponse> ById(ID id);
}
