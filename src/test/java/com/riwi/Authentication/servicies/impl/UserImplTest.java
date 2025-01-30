package com.riwi.Authentication.servicies.impl;

import com.riwi.Authentication.Exception.ApiException;
import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.models.UserEntity;
import com.riwi.Authentication.repositories.UserRepository;
import com.riwi.Authentication.servicies.impl.UserImpl;
import com.riwi.Authentication.utils.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserImpl userService; // Usa el nombre de la clase de implementación

    @Test
    void testCreateUser() {
        // 1. Arrange (Preparación)
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Test User");
        userRequest.setLastname("Test Lastname");
        userRequest.setEmail("test@example.com");
        userRequest.setPassword("password123");
        userRequest.setAccess_token("token123");
        userRequest.setRole(Role.CLIENT);

        UserEntity userEntityToSave = new UserEntity(); // Entidad para guardar
        userEntityToSave.setName(userRequest.getName());
        userEntityToSave.setLastname(userRequest.getLastname());
        userEntityToSave.setEmail(userRequest.getEmail());
        userEntityToSave.setPassword(userRequest.getPassword());
        userEntityToSave.setAccess_token(userRequest.getAccess_token());
        userEntityToSave.setRole(userRequest.getRole());

        UserEntity savedUserEntity = new UserEntity(); // Entidad después de guardar
        savedUserEntity.setId(1L); // Simula el ID generado por la base de datos
        savedUserEntity.setName(userRequest.getName());
        savedUserEntity.setLastname(userRequest.getLastname());
        savedUserEntity.setEmail(userRequest.getEmail());
        savedUserEntity.setPassword(userRequest.getPassword());
        savedUserEntity.setAccess_token(userRequest.getAccess_token());
        savedUserEntity.setRole(userRequest.getRole());

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

        // 2. Act (Ejecución)
        UserEntity createdUser = userService.create(userRequest); // Usa userService (la instancia real)

        // 3. Assert (Verificación)

        // Verifica que el repositorio llamó al método save (esto ya lo hacía bien)
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity capturedUserEntity = userEntityCaptor.getValue();

        // Verifica que los datos guardados son correctos (comparando con userRequest)
        assertEquals(userRequest.getName(), capturedUserEntity.getName());
        assertEquals(userRequest.getLastname(), capturedUserEntity.getLastname());
        assertEquals(userRequest.getEmail(), capturedUserEntity.getEmail());
        assertEquals(userRequest.getAccess_token(), capturedUserEntity.getAccess_token());
        assertEquals(userRequest.getRole(), capturedUserEntity.getRole());
        assertEquals(userRequest.getPassword(), capturedUserEntity.getPassword());

        // Verifica que el servicio retorna la entidad guardada (comparando con savedUserEntity)
        assertEquals(savedUserEntity.getId(), createdUser.getId());
        assertEquals(savedUserEntity.getName(), createdUser.getName());
        assertEquals(savedUserEntity.getLastname(), createdUser.getLastname());
        assertEquals(savedUserEntity.getEmail(), createdUser.getEmail());
        assertEquals(savedUserEntity.getAccess_token(), createdUser.getAccess_token());
        assertEquals(savedUserEntity.getRole(), createdUser.getRole());
        assertEquals(savedUserEntity.getPassword(), createdUser.getPassword());
    }


    //ById
    @Test
    void testReadById_userExists() {
        // Arrange
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setName("Test User");
        userEntity.setLastname("Test Lastname");
        userEntity.setEmail("test@example.com");
        userEntity.setAccess_token("test_token");
        userEntity.setRole(Role.CLIENT); // Replace with your actual Role enum

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // Act
        UserResponse userResponse = userService.readById(userId);

        // Assert
        assertNotNull(userResponse);
        assertEquals(userId, userResponse.getId());
        assertEquals("Test User", userResponse.getName());
        assertEquals("Test Lastname", userResponse.getLastname());
        assertEquals("test@example.com", userResponse.getEmail());
        assertEquals("test_token", userResponse.getAccess_token());
        assertEquals(Role.CLIENT, userResponse.getRole()); // Replace with your actual Role enum
    }

    @Test
    void testReadById_userNotFound() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> userService.readById(userId));
    }


    //delete
    @Test
    void testDeleteUser_userExists() {
        // Arrange
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository).deleteById(userId); // Verifica que se llamó deleteById
    }

    @Test
    void testDeleteUser_userNotFound() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty()); // Simula que no encuentra el usuario

        // Act & Assert
        assertThrows(ApiException.class, () -> userService.delete(userId)); // Espera que lance la excepción
        verify(userRepository, never()).deleteById(userId); // Verifica que NO se llamó deleteById
    }



    //path


    @Test
    void testPathUser_userExists() {
        // Arrange
        Long userId = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Updated Name");
        userRequest.setLastname("Updated Lastname");
        userRequest.setEmail("updated@example.com");
        userRequest.setAccess_token("updated_token");
        userRequest.setRole(Role.ADMIN); // Replace with your Role enum
        userRequest.setPassword("updated_password"); // Should be hashed in real app

        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setName("Original Name");
        existingUser.setLastname("Original Lastname");
        existingUser.setEmail("original@example.com");
        existingUser.setAccess_token("original_token");
        existingUser.setRole(Role.CLIENT); // Replace with your Role enum
        existingUser.setPassword("original_password"); // Should be hashed in real app


        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // Act
        userService.path(userRequest, userId);

        // Assert
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityCaptor.capture());

        UserEntity updatedUser = userEntityCaptor.getValue();

        assertEquals(userId, updatedUser.getId());
        assertEquals(userRequest.getName(), updatedUser.getName());
        assertEquals(userRequest.getLastname(), updatedUser.getLastname());
        assertEquals(userRequest.getEmail(), updatedUser.getEmail());
        assertEquals(userRequest.getAccess_token(), updatedUser.getAccess_token());
        assertEquals(userRequest.getRole(), updatedUser.getRole());
        assertEquals(userRequest.getPassword(), updatedUser.getPassword()); // Handle hashing if needed

    }


    @Test
    void testPathUser_userNotFound() {
        // Arrange
        Long userId = 1L;
        UserRequest userRequest = new UserRequest(); // Aunque no se usará, es necesario instanciarlo

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> userService.path(userRequest, userId));
        verify(userRepository, never()).save(any(UserEntity.class)); // Verifica que NO se llamó save
    }


    // put


    @Test
    void testUpdateUser_userExists() {
        // Arrange
        Long userId = 1L;
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Updated Name");
        userRequest.setLastname("Updated Lastname");
        userRequest.setEmail("updated@example.com");
        userRequest.setPassword("updated_password"); // Should be hashed in real app
        userRequest.setAccess_token("updated_token");
        userRequest.setRole(Role.ADMIN); // Replace with your Role enum


        UserEntity existingUser = new UserEntity();
        existingUser.setId(userId);
        existingUser.setName("Original Name");
        existingUser.setLastname("Original Lastname");
        existingUser.setEmail("original@example.com");
        existingUser.setPassword("original_password"); // Should be hashed in real app
        existingUser.setAccess_token("original_token");
        existingUser.setRole(Role.CLIENT); // Replace with your Role enum

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser); // Mock save to return the same user


        // Act
        userService.update(userRequest, userId);

        // Assert
        ArgumentCaptor<UserEntity> userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userEntityCaptor.capture());

        UserEntity updatedUser = userEntityCaptor.getValue();

        assertEquals(userId, updatedUser.getId());
        assertEquals(userRequest.getName(), updatedUser.getName());
        assertEquals(userRequest.getLastname(), updatedUser.getLastname());
        assertEquals(userRequest.getEmail(), updatedUser.getEmail());
        assertEquals(userRequest.getPassword(), updatedUser.getPassword()); // Handle hashing if needed
        assertEquals(userRequest.getAccess_token(), updatedUser.getAccess_token());
        assertEquals(userRequest.getRole(), updatedUser.getRole());

    }


    @Test
    void testUpdateUser_userNotFound() {
        // Arrange
        Long userId = 1L;
        UserRequest userRequest = new UserRequest(); // You can populate it if needed

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ApiException.class, () -> userService.update(userRequest, userId));
    }

    @Test
    void testReadAllUsers() {
        // Arrange
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("Test User 1");
        user1.setLastname("Test Lastname 1");
        user1.setEmail("test1@example.com");
        user1.setAccess_token("token1");
        user1.setRole(Role.CLIENT); // Replace with your Role enum

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setName("Test User 2");
        user2.setLastname("Test Lastname 2");
        user2.setEmail("test2@example.com");
        user2.setAccess_token("token2");
        user2.setRole(Role.ADMIN); // Replace with your Role enum

        List<UserEntity> userEntities = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userEntities);

        // Act
        List<UserResponse> userResponses = userService.readAll();

        // Assert
        assertEquals(2, userResponses.size());

        UserResponse response1 = userResponses.get(0);
        assertEquals(1L, response1.getId());
        assertEquals("Test User 1", response1.getName());
        assertEquals("Test Lastname 1", response1.getLastname());
        assertEquals("test1@example.com", response1.getEmail());
        assertEquals("token1", response1.getAccess_token());
        assertEquals(Role.CLIENT, response1.getRole()); // Replace with your Role enum

        UserResponse response2 = userResponses.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("Test User 2", response2.getName());
        assertEquals("Test Lastname 2", response2.getLastname());
        assertEquals("test2@example.com", response2.getEmail());
        assertEquals("token2", response2.getAccess_token());
        assertEquals(Role.ADMIN, response2.getRole()); // Replace with your Role enum
    }
}