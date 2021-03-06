package com.thebestdevelopers.find_my_beer.DTO;

/**
* @author Jakub Pisula
*/
public class PubDTO {
    private long id;
    private String pubName;
    private String gks;

    public PubDTO() {
    }

    public PubDTO(long user_id, String pubName) {
        this.id = user_id;
        this.pubName = pubName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getGks() {
        return gks;
    }

    public void setGks(String gks) {
        this.gks = gks;
    }
}

