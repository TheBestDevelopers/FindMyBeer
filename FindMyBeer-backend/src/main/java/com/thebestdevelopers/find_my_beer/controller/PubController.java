package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.*;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;
import com.thebestdevelopers.find_my_beer.controller.pubControllerParam.createPubParam;
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
    public PubDTO createUser(@Valid @RequestBody createPubParam param) {
        return pubService.createPub(param.getPubName());
    }

    @GetMapping("getPubInfo")
    public PubInfoDTO getPubInfo(@RequestParam("userID") int userId, @RequestParam("pubID") int pubId, Principal principal){
        //User user = (User) ((Authentication) principal).getPrincipal();
        //UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubInfo(userId, pubId);
    }

    @GetMapping("getPubView")
    public PubInfoDTO getPubView(@RequestParam("pubID") int pubId, Principal principal){
        //User user = (User) ((Authentication) principal).getPrincipal();
        //UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubInfo(-1, pubId);
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

    @GetMapping("setConveniences")
    public Boolean setConveniences(@RequestParam("pubID") int pubId,
                                   @RequestParam("true") String[] convToAdd,
                                   @RequestParam("false") String[] convToDelete,
                                   Principal principal) throws IOException {
        //User user = (User) ((Authentication) principal).getPrincipal();
        //if(!user.getUsername().equals(param.getUsername()))
        //throw new UserDeclinedException("You don't have permission");

        return pubService.setConveniences(pubId, convToAdd, convToDelete);
    }

    @GetMapping("setTables")
    public BooleanDTO setTables(@RequestParam("pubID") int pubId,
                                @RequestParam("chair1") int chair1,
                                @RequestParam("chair2") int chair2,
                                @RequestParam("chair4") int chair4,
                                @RequestParam("chair6") int chair6,
                                @RequestParam("chair8") int chair8,
                                Principal principal){
        return pubService.setTables(pubId, chair1, chair2, chair4, chair6, chair8);
    }
}
