package com.riwi.Authentication.servicies.generics;

public interface ReadById<ID,EntityResponse>{
    public EntityResponse readById(ID id);
}
