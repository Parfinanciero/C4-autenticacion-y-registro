package com.riwi.Authentication.servicies.generics;

public interface Path<EntityRequest,ID> {
    public void path(EntityRequest entityRequest, ID id);
}
