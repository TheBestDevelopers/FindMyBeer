package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.*;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;
import com.thebestdevelopers.find_my_beer.controller.pubControllerParam.CreatePubParam;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import com.thebestdevelopers.find_my_beer.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;

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
        //User user = (User) ((Authentication) principal).getPrincipal();
        //UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubInfo(userId, pubId);
    }

    @GetMapping("getPubMenu")
    public PubMenuDTO getPubMenu(@RequestParam("pubID") int pubId, Principal principal){
        //User user = (User) ((Authentication) principal).getPrincipal();
        //UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubMenu(pubId);
    }

    @GetMapping("getPubs")
    public GetPubsDTO getPubs(@RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude) throws IOException{
        //User user = (User) ((Authentication) principal).getPrincipal();
        //UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubs(longitude, latitude);
    }

    @GetMapping("getNearestPubs")
    public List<GetNearestPubDTO> getNearestPubs(@RequestParam("longitude") Double longitude,
                                                 @RequestParam("latitude") Double latitude,
                                                 Principal principal) throws IOException {
        //User user = (User) ((Authentication) principal).getPrincipal();
        //if(!user.getUsername().equals(param.getUsername()))
        //throw new UserDeclinedException("You don't have permission");

        return pubService.getNearestPubs(longitude, latitude);
    }

    @GetMapping("getPubsWithConveniences")
    public List<GetNearestPubDTO> getPubsWithConveniences(@RequestParam("longitude") Double longitude,
                                                   @RequestParam("latitude") Double latitude,
                                                   @RequestParam("conveniences") String[] conveniences,
                                                   Principal principal) throws IOException {
        //User user = (User) ((Authentication) principal).getPrincipal();
        //if(!user.getUsername().equals(param.getUsername()))
        //throw new UserDeclinedException("You don't have permission");

        return pubService.getPubsWithConveniences(longitude, latitude, conveniences);
    }
}
