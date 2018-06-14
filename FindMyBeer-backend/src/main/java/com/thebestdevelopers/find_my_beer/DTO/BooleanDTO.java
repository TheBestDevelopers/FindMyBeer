package com.thebestdevelopers.find_my_beer.DTO;

public class BooleanDTO {
    private Boolean result;

    public BooleanDTO(){
        this.result = false;
    }

    public BooleanDTO(Boolean result){
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}
