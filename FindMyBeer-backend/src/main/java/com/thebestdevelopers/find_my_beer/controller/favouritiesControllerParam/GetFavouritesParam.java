package com.thebestdevelopers.find_my_beer.controller.favouritiesControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class GetFavouritesParam implements Serializable {
    @NotNull
    private int clientId;

    @JsonCreator
    public GetFavouritesParam(@JsonProperty("clientId") int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() { return this.clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

}
