package com.riwi.Authentication.servicies.impl;

import com.riwi.Authentication.Exception.ApiException;
import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.models.UserEntity;
import com.riwi.Authentication.repositories.UserRepository;
import com.riwi.Authentication.servicies.interfaces.IUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserImpl implements IUService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity create(UserRequest userRequest) {

        // dto --> entidad

        UserEntity usuario = new UserEntity();

                usuario.setName(userRequest.getName());
                usuario.setLastname(userRequest.getLastname());
                usuario.setEmail(userRequest.getEmail());
                usuario.setPassword(userRequest.getPassword()); // se estan hasheando?
                usuario.setAccess_token(userRequest.getAccess_token());
                usuario.setRole(userRequest.getRole());
                //.password(passwordEncoder.encode(request.getPassword())) // agregar encriptacion de contraseña

        return userRepository.save(usuario);
    }

    @Override
    public void delete(Long id) {

        UserEntity usuario= userRepository.findById(id).orElseThrow(()-> new ApiException("El usuario no existe"));
        userRepository.deleteById(id);
    }

    @Override
    public void path(UserRequest userRequest, Long id) {

        UserEntity usuario=userRepository.findById(id).orElseThrow(
                ()-> new ApiException("El usuario no existe")
        );

        if(userRequest.getName()!= null){
            usuario.setName(userRequest.getName());
        }
        if(userRequest.getLastname()!= null){
            usuario.setLastname(userRequest.getLastname());
        }
        if(userRequest.getEmail()!= null){
            usuario.setEmail(userRequest.getEmail());
        }

        if(userRequest.getAccess_token()!=null){
            usuario.setAccess_token(userRequest.getAccess_token());
        }

        if(userRequest.getRole()!= null){
            usuario.setRole(userRequest.getRole());

        }

        if (userRequest.getPassword() != null){
            usuario.setPassword(userRequest.getPassword());
        }


//        if(userRequest.getPassword()!=null){
//            usuario.setPassword(passwordEncoder.encode(userRequest.getPassword())); //passwordENCODER
//        }

        userRepository.save(usuario);
    }

    @Override
    public void update(UserRequest userRequest, Long id) {

        UserEntity usuario=userRepository.findById(id).orElseThrow(
//                ()-> new ApiException("El usuario no existe")
        );

        usuario.setName(userRequest.getName());
        usuario.setLastname(userRequest.getLastname());
        usuario.setEmail(userRequest.getEmail());
        usuario.setPassword(userRequest.getPassword());
        usuario.setAccess_token(userRequest.getAccess_token());
        usuario.setRole(userRequest.getRole());

        //usuario.setPassword(passwordEncoder.encode(request.getPassword())); // hasear

        userRepository.save(usuario);
    }

    @Override
    public List<UserResponse> readAll() {
        return userRepository.findAll().stream()
                .map(usuario-> {
                    UserResponse userResponse= new UserResponse();

                    userResponse.setId(usuario.getId());
                    userResponse.setName(usuario.getName());
                    userResponse.setLastname(usuario.getLastname());
                    userResponse.setEmail(usuario.getEmail());
                    //userResponse.setPassword(usuario.getPassword());
                    userResponse.setAccess_token(usuario.getAccess_token());
                    userResponse.setRole(usuario.getRole());
                    return userResponse;
                }).collect(Collectors.toList());
    }

    @Override
    public UserResponse readById(Long id) {

        //entidad --> userResponse
        UserEntity usuario= userRepository.findById(id).orElseThrow(
               ()-> new ApiException("El usuario no existe")
        );


        UserResponse userResponse= new UserResponse();

        //setting


        userResponse.setId(usuario.getId());
        userResponse.setName(usuario.getName());
        userResponse.setLastname(usuario.getLastname());
        userResponse.setEmail(usuario.getEmail());
        //userResponse.setPassword(usuario.getPassword());
        userResponse.setAccess_token(usuario.getAccess_token());
        userResponse.setRole(usuario.getRole());

        return userResponse;
    }
}
