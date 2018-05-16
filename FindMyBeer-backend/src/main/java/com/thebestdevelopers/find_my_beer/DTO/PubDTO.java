package com.thebestdevelopers.find_my_beer.DTO;

/**
* @author Jakub Pisula
*/
public class PubDTO {
    private long id;
    private String pubName;

    public PubDTO() {
    }

    public PubDTO(long user_id, String pubName) {
        this.id = user_id;
        this.pubName = pubName;
    }
}
