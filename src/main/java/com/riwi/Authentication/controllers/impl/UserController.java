package com.riwi.Authentication.controllers.impl;

import com.riwi.Authentication.Exception.ApiException;
import com.riwi.Authentication.controllers.interfaces.InterfaceUserController;
import com.riwi.Authentication.dtos.requests.UserRequest;
import com.riwi.Authentication.dtos.response.UserResponse;
import com.riwi.Authentication.servicies.interfaces.IUService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController implements InterfaceUserController {

    @Autowired
    IUService iuService;


    @PostMapping
    @Operation(summary = "Create a new user", description = "This endpoint creates a new user and returns a confirmation message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Override
    public ResponseEntity<String> create(
            @Parameter(description = "Detalles del usuario para crear un nuevo usuario", required = true,
                    schema = @Schema(implementation = UserRequest.class))
            @Valid @RequestBody UserRequest entity) {
        iuService.create(entity);
        return ResponseEntity.ok("User successfully created");
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a user by ID", description = "This endpoint retrieves a user by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user ID supplied",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> ById(
            @Parameter(description = "The unique ID of the user to retrieve", required = true, example = "123") // Add @Parameter
            @PathVariable Long id){
        UserResponse response = iuService.readById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user by ID", description = "This endpoint deletes a user by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiException.class))), // Or a more specific error class
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiException.class))), // Or a more specific error class
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) // No schema needed for 500, or specific error class
    })
    public ResponseEntity<String> delete(
            @Parameter(description = "The unique ID of the user to delete", required = true, example = "123")
            @PathVariable Long id) {
        iuService.delete(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    @Override
    @PatchMapping("/path/{id}")
    @Operation(summary = "Update a user by ID", description = "This endpoint updates an existing user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data or ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiException.class))), // Or a more specific error class
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiException.class))), // Or a more specific error class
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) // No schema needed for 500, or specific error class
    })
    public ResponseEntity<String> path(
            @Parameter(description = "User details to update", required = true, schema = @Schema(implementation = UserRequest.class))
            @RequestBody @Valid UserRequest userRequest,
            @Parameter(description = "The unique ID of the user to update", required = true, example = "123")
            @PathVariable Long id) {
        iuService.path(userRequest, id);
        return ResponseEntity.ok("User successfully update");
    }

    @PutMapping("/update/{id}")
    @Override
    @Operation(summary = "Update a user by ID", description = "This endpoint updates an existing user's information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user data or ID supplied",
                    content = @Content(schema = @Schema(implementation = ApiException.class))), // Replace with your error class
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ApiException.class))), // Replace with your error class
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) // Or specific error schema
    })
    public ResponseEntity<String> update(
            @Parameter(description = "User details to update", required = true, schema = @Schema(implementation = UserRequest.class))
            @RequestBody @Valid UserRequest userRequest,
            @Parameter(description = "The unique ID of the user to update", required = true, example = "123")
            @PathVariable Long id) {
            iuService.update(userRequest, id);
            return ResponseEntity.ok("user successfully updated");
    }

    @Override
    @GetMapping
    @Operation(summary = "Retrieve all users", description = "This endpoint retrieves a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(type = "array", implementation = UserResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content) // Or specific error schema if needed
    })
    public ResponseEntity<List<UserResponse>> readAll() {

        return ResponseEntity.ok(iuService.readAll());

    }

}
