package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.ConveniencesDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubInfoDTO;
import com.thebestdevelopers.find_my_beer.service.ConveniencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;

/**
 * @author Grzegorz Nowak
 *
 */

@RestController
@RequestMapping("api/conveniences/")
public class ConveniencesController implements Serializable{

    @Autowired
    ConveniencesService conveniencesService;

    @GetMapping("getConveniences")
    public ConveniencesDTO getAllConveniences(){

        return conveniencesService.getAllConveniences();
    }

    @GetMapping("getPubConveniences")
    public ConveniencesDTO getPubConveniences(@RequestParam("pubID") int pubId){

        return conveniencesService.getPubConveniences(pubId);
    }



}
