package com.thebestdevelopers.find_my_beer.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.scene.effect.Bloom;

import java.util.List;


/**
 * @author Grzegorz Nowak
 *
 */
public class ConveniencesDTO {

    @JsonProperty("result")
    List<String> convenienceName;

    public ConveniencesDTO() {
    }

    public ConveniencesDTO(List<String> convenienceName) {
        this.convenienceName = convenienceName;
    }

    public  List<String> getConvenienceName() {
        return convenienceName;
    }

    public void setConvenienceName(List<String> convenienceName) {
        this.convenienceName = convenienceName;
    }
}
