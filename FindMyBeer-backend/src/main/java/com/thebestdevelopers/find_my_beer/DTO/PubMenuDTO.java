package com.thebestdevelopers.find_my_beer.DTO;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Map;
import java.util.TreeMap;

/**
 *   @author Grzegorz Nowak
 */
public class PubMenuDTO {

    private Map<String, Double> result;

    public PubMenuDTO() {
        this.result = new TreeMap<>();
        result.put("Product not found", 0.0);
    }

    public PubMenuDTO(Map<String, Double> productsAndPrices) {
        this.result = productsAndPrices;
    }

    public Map<String, Double> getResult() {
        return result;
    }

    public void setResult(Map<String, Double> result) {
        this.result = result;
    }
}
