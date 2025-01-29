package com.riwi.Authentication.dtos.requests;


import com.riwi.Authentication.utils.enums.Role;
import jakarta.persistence.*;
import lombok.*;



public class UserRequest {


    private String name;
    private String lastname;
    private String password;
    private String email;
    private String access_token;
    private Role role;

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public UserRequest(String name, String lastname, String password, String email, String access_token, Role role) {
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.access_token = access_token;
        this.role = role;
    }

    public UserRequest() {
    }
}
