package com.riwi.Authentication.servicies.interfaces;

import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.models.UserEntity;
import com.riwi.Authentication.servicies.generics.*;

public interface IUService extends Create<UserRequest, UserEntity>,
        ReadById<Long, UserResponse>,
        Delete<Long>,
        ReadAll<UserResponse>, Put<UserRequest,Long>,
        Path<UserRequest,Long> {
}
