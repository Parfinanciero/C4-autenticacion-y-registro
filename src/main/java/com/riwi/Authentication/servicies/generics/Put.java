package com.riwi.Authentication.servicies.generics;

public interface Put<EntityRequest,ID> {
    public void update(EntityRequest entityRequest,ID id);
}
