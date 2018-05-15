package com.thebestdevelopers.find_my_beer.controller.userControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Jakub Pisula
 */
public class EditUserParam implements Serializable {
    private String username;
    private String password;
    private String newPassword;


    @JsonCreator
    public EditUserParam(@JsonProperty("username") String username,
                        @JsonProperty("password") String password,
                        @JsonProperty("newPassword") String newPassword){
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
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

    public String getNewPassword() {
        return newPassword;
    }
}
