package com.riwi.Authentication.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.servicies.interfaces.IUService;
import com.riwi.Authentication.utils.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.management.relation.RoleStatus;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


class UserControllerTest {


    @Mock
    private IUService iuService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {

        ObjectMapper objectMappers = new ObjectMapper();
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setLastname("Doe");
        userRequest.setPassword("password123");
        userRequest.setEmail("john.doe@example.com");
        userRequest.setAccess_token("access_token_example");
        userRequest.setRole(Role.CLIENT);


        String userRequestJson = objectMappers.writeValueAsString(userRequest);


        System.out.println("JSON Payload: " + userRequestJson); // Imprime el JSON para verificar

        // Mock del servicio para que no haga nada
        doAnswer(invocation -> {
            return null;
        }).when(iuService).create(any(UserRequest.class));


        // Realiza la solicitud POST y verifica el estado de respuesta
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetUserById() throws Exception {
        // 1. Configura el mock del servicio
        Long userId = 5L; // ID de usuario de ejemplo
        UserResponse mockResponse = new UserResponse(); // Crea un objeto UserResponse simulado
        mockResponse.setId(userId);
        mockResponse.setName("John");
        mockResponse.setLastname("Doe");
        mockResponse.setEmail("john.doe@example.com");
        mockResponse.setAccess_token("access_token_example");
        mockResponse.setRole(Role.CLIENT);

        Mockito.when(iuService.readById(userId)).thenReturn(mockResponse);

        // 2. Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/users/{id}", userId) // Ajusta la URL según tu API
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verifica el tipo de contenido
                .andExpect(content().json(asJsonString(mockResponse))); // Verifica el contenido JSON

    }



    @Test
    public void testDeleteUser() throws Exception {
        // 1. Configura el mock del servicio
        Long userId = 123L; // ID de usuario de ejemplo

        doNothing().when(iuService).delete(userId); // Usa doNothing para métodos void

        // 2. Realiza la solicitud DELETE y verifica la respuesta
        mockMvc.perform(delete("/api/users/delete/{id}", userId) // Ajusta la URL según tu API
                        .contentType(MediaType.APPLICATION_JSON)) // Si envías algo en el body, especifica el tipo
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully deleted")); // Verifica el mensaje de éxito

        //finalmente sse verifico el estado de la solicitud delete que debe tener 200 y un cuerpo con el mensaje anterior. Pero este mensaje no se imprime por consola
    }

    @Test
    public void testPathUser() throws Exception {
        Long userId = 123L;

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Updated Name"); // seleccionamos este valor para seet un atributo, deberia aplicar con los otros campos, pero la idea es hacerlo con uno


        ObjectMapper objectMapper = new ObjectMapper();
        String userRequestJson = objectMapper.writeValueAsString(userRequest);


        doNothing().when(iuService).path(Mockito.any(UserRequest.class), Mockito.eq(userId));


        mockMvc.perform(patch("/api/users/path/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("User successfully update"));
    }


    @Test
    public void testUpdateUser() throws Exception {
        Long userId = 123L;

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Updated Name");
        userRequest.setLastname("Updated Lastname");
        userRequest.setPassword("Updated Password");
        userRequest.setEmail("Updated Email");
        userRequest.setAccess_token("Updated Access Token");
        userRequest.setRole(Role.ADMIN);


        ObjectMapper objectMapper = new ObjectMapper();
        String userRequestJson = objectMapper.writeValueAsString(userRequest);

        doNothing().when(iuService).update(Mockito.any(UserRequest.class), Mockito.eq(userId));

        mockMvc.perform(put("/api/users/update/{id}", userId) // Use put for update
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("user successfully updated"));
    }


    @Test
    public void testReadAllUsers() throws Exception {

        // 1. Configura el mock del servicio
        UserResponse user1 = new UserResponse();
        user1.setId(1L);
        user1.setName("John");
        user1.setLastname(" Doe");
        user1.setEmail("John.doe@example.com");
        user1.setAccess_token("no vas a entrar");
        user1.setRole(Role.CLIENT);

        UserResponse user2 = new UserResponse();
        user2.setId(2L);
        user2.setName("Jane");
        user2.setLastname("Doe");
        user2.setEmail("Jane.doe@example.com");
        user2.setAccess_token("tu menos entras");
        user2.setRole(Role.CLIENT);

        List<UserResponse> mockResponse = Arrays.asList(user1, user2);

        Mockito.when(iuService.readAll()).thenReturn(mockResponse);

        // 2. Realiza la solicitud GET y verifica la respuesta
        mockMvc.perform(get("/api/users") // Adjust the URL if needed
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(asJsonString(mockResponse))); // Use asJsonString to compare JSON
    }

    // Método auxiliar para convertir objetos a JSON (usando ObjectMapper)
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}