package com.thebestdevelopers.find_my_beer.controller.pubControllerParam;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class createPubParam implements Serializable {
    private String pubName;


    public createPubParam() {
    }

    @JsonCreator
    public createPubParam(@JsonProperty("pubName") String pubName){
        this.pubName = pubName;


    }

    public String getPubName() {
        return this.pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }
//
//    public String getPhoto() {
//        return this.photo;
//    }
//
//    public void setPhoto(String photo) {
//        this.photo = photo;
//   }
}
