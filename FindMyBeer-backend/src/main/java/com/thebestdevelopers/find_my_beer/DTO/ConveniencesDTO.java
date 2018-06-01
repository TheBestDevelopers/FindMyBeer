package com.thebestdevelopers.find_my_beer.DTO;

import javafx.scene.effect.Bloom;

public class ConveniencesDTO {
    String convenienceName;
    boolean isPubHaveConvebience = false;

    public ConveniencesDTO() {
    }

    public ConveniencesDTO(String convenienceName, boolean isPubHaveConvebience) {
        this.convenienceName = convenienceName;
        this.isPubHaveConvebience = isPubHaveConvebience;
    }

    public String getConvenienceName() {
        return convenienceName;
    }

    public void setConvenienceName(String convenienceName) {
        this.convenienceName = convenienceName;
    }

    public boolean getIsPubHaveConvebience() {
        return isPubHaveConvebience;
    }

    public void setIsPubHaveConvebience(boolean isPubHaveConvebience) {
        this.isPubHaveConvebience = isPubHaveConvebience;
    }
}
