package com.riwi.Authentication.servicies.generics;

public interface Create<EntityRequest,Entity> {

    public Entity create(EntityRequest request);

}
