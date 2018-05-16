package com.thebestdevelopers.find_my_beer.controller.favouritiesControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AddFavouritesParam implements Serializable {
    @NotNull
    private int clientId;
    @NotNull
    private int pubId;

    @JsonCreator
    public AddFavouritesParam(@JsonProperty("clientId") int clientId,
                              @JsonProperty("pubId") int pubId) {
        this.clientId = clientId;
        this.pubId = pubId;
    }

    public int getClientId() { return this.clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setPubId(int pubId) {
        this.pubId = pubId;
    }
    public int getPubId() {
        return pubId;
    }
}
