package com.thebestdevelopers.find_my_beer.controller;

import com.sun.deploy.security.UserDeclinedException;
import com.thebestdevelopers.find_my_beer.DTO.PubDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubInfoDTO;
import com.thebestdevelopers.find_my_beer.DTO.PubMenuDTO;
import com.thebestdevelopers.find_my_beer.controller.pubControllerParam.CreatePubParam;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import com.thebestdevelopers.find_my_beer.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.security.Principal;

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

    @Autowired
    UserRepository userRepository;

    @PostMapping("new")
    public PubDTO createUser(@Valid @RequestBody CreatePubParam param) {
        return pubService.createPub(param.getPubName());
    }

    @GetMapping("getPubInfo")
    public PubInfoDTO getPubInfo(@RequestParam("userID") int userId, @RequestParam("pubID") int pubId, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubInfo((int)userEntity.getUserId(), pubId);
    }

    @GetMapping("getPubMenu")
    public PubMenuDTO getPubMenu(@RequestParam("pubID") int pubId, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubMenu(pubId);
    }
}
