package com.thebestdevelopers.find_my_beer.controller.userControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Dominik Florencki
 */
public class CreateUserParam implements Serializable {
    private String username;
    private String password;
    private String role;

    public CreateUserParam() {
    }
    @JsonCreator
    public CreateUserParam(@JsonProperty("username") String username,
                           @JsonProperty("password") String password,
                           @JsonProperty("role")  String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
