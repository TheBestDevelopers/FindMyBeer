package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.PubDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubInfoDTO;
import com.thebestdevelopers.find_my_beer.controller.pubControllerParam.CreatePubParam;
import com.thebestdevelopers.find_my_beer.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
/**
 * @author Jakub Pisula
 * Modified by: Dominik Florencki, Grzegorz Nowak
 * The Controller to manage the pub account ONLY
 */

@RestController
@RequestMapping("api/pubs/")
public class PubController implements Serializable {
    @Autowired
    PubService pubService;

    @PostMapping("new")
    public PubDTO createUser(@Valid @RequestBody CreatePubParam param) {
        return pubService.createPub(param.getPubName());
    }

    @GetMapping("getPubInfo")
    public PubInfoDTO getPubInfo(@RequestParam("userID") int userId, @RequestParam("pubID") int pubId){
        return pubService.getPubInfo(userId, pubId);
    }

}
