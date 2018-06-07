package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.ConveniencesDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubInfoDTO;
import com.thebestdevelopers.find_my_beer.service.ConveniencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ConveniencesDTO getAllConveniences( Principal principal){
        //User user = (User) ((Authentication) principal).getPrincipal();
        //if(!user.getUsername().equals(param.getUsername()))
        //throw new UserDeclinedException("You don't have permission");

        return conveniencesService.getAllConveniences();
    }

    @GetMapping("getPubConveniences")
    public ConveniencesDTO getPubConveniences(@RequestParam("pubID") int pubId, Principal principal){
        //User user = (User) ((Authentication) principal).getPrincipal();
        //if(!user.getUsername().equals(param.getUsername()))
        //throw new UserDeclinedException("You don't have permission");

        return conveniencesService.getPubConveniences(pubId);
    }
}
