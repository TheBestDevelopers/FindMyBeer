package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.*;
import com.thebestdevelopers.find_my_beer.DTO.getPubsDTOs.GetPubsDTO;
import com.thebestdevelopers.find_my_beer.controller.pubControllerParam.createPubParam;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import com.thebestdevelopers.find_my_beer.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public PubInfoDTO getPubInfo(@RequestParam("pubID") int pubId, Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubInfo((int) userEntity.getUserId(), pubId);
    }

    @GetMapping("getPubView")
    public PubInfoDTO getPubView(Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.getPubInfo(-1, (int) userEntity.getUserId());
    }

    @GetMapping("getPubMenu")
    public PubMenuDTO getPubMenu(@RequestParam("pubID") int pubId){

        return pubService.getPubMenu(pubId);
    }

    @GetMapping("getPubs")
    public GetPubsDTO getPubs(@RequestParam("longitude") Double longitude, @RequestParam("latitude") Double latitude) throws IOException{

        return pubService.getPubs(longitude, latitude);
    }

    @GetMapping("getNearestPubs")
    public List<GetNearestPubDTO> getNearestPubs(@RequestParam("longitude") Double longitude,
                                                 @RequestParam("latitude") Double latitude,
                                                 Principal principal) throws IOException {
        return pubService.getNearestPubs(longitude, latitude);
    }

    @GetMapping("getPubsWithConveniences")
    public List<GetNearestPubDTO> getPubsWithConveniences(@RequestParam("longitude") Double longitude,
                                                          @RequestParam("latitude") Double latitude,
                                                          @RequestParam("conveniences") String[] conveniences) throws IOException {

        return pubService.getPubsWithConveniences(longitude, latitude, conveniences);
    }

    @GetMapping("setConveniences")
    public Boolean setConveniences(@RequestParam("WI-FI") Boolean wifi,
                                   @RequestParam("TOILET") Boolean toilet,
                                   @RequestParam("ROASTING_ROOM") Boolean roastingRoom,
                                   @RequestParam("DISCOUNTS_FOR_STUDENTS") Boolean discountsForStudents,
                                   @RequestParam("FACILITIES_FOR_THE_DISABLED") Boolean facilitiesForTheDisabled,
                                   @RequestParam("BOARD_GAMES") Boolean boardGames,
                                   Principal principal) throws IOException {

		String[] convenienceNames = {"WI-FI", "TOILET", "ROASTING_ROOM", "DISCOUNTS_FOR_STUDENTS", "FACILITIES_FOR_THE_DISABLED", "BOARD_GAMES"};
        Map<String, Boolean> convenienceNamesAndValues = new TreeMap<>();
        convenienceNamesAndValues.put(convenienceNames[0], wifi);
        convenienceNamesAndValues.put(convenienceNames[1], toilet);
        convenienceNamesAndValues.put(convenienceNames[2], roastingRoom);
        convenienceNamesAndValues.put(convenienceNames[3], discountsForStudents);
        convenienceNamesAndValues.put(convenienceNames[4], facilitiesForTheDisabled);
        convenienceNamesAndValues.put(convenienceNames[5], boardGames);

        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.setConveniences((int) userEntity.getUserId(), convenienceNames, convenienceNamesAndValues);
    }

    @GetMapping("setTables")
    public BooleanDTO setTables(@RequestParam("chair1") int chair1,
                                @RequestParam("chair2") int chair2,
                                @RequestParam("chair4") int chair4,
                                @RequestParam("chair6") int chair6,
                                @RequestParam("chair8") int chair8,
                                Principal principal){

        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return pubService.setTables((int) userEntity.getUserId(), chair1, chair2, chair4, chair6, chair8);
    }
}