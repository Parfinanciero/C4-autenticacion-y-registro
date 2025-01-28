package com.riwi.Authentication.dtos.response;


import com.riwi.Authentication.utils.enums.Role;
import jakarta.persistence.Column;
import lombok.*;


public class UserResponse {

    private Long id; // deberia ir?
    private String name;
    private String lastname;
    private String password;
    private String email;
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserResponse(Long id, String name, String lastname, String password, String email, Role role) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserResponse() {
    }
}
