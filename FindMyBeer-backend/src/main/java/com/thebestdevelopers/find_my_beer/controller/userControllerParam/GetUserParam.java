package com.thebestdevelopers.find_my_beer.controller.userControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Dominik Florencki
 */
public class GetUserParam implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String password;


    @JsonCreator
    public GetUserParam(@JsonProperty("username") String username,
                        @JsonProperty("password") String password){
        this.username = username;
        this.password = password;
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

}
