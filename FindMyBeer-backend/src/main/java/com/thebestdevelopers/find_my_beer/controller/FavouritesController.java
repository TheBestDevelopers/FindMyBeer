package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import com.thebestdevelopers.find_my_beer.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * @author Jakub Pisula
 * The Controller to manage user's favourite pubs
 */
@RestController
@RequestMapping("api/favourites/")
public class FavouritesController {

    @Autowired
    FavouritesService favService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("getFavourites")
    public List<FavResult> getFavouritePubs( Principal principal) throws IOException {
        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return favService.getFavouritePubs((int) userEntity.getUserId());
    }

    @GetMapping("addFavourite")
    public BooleanDTO addFavourite(@RequestParam("pubID") int pubId, Principal principal){

        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return favService.addFavourite((int) userEntity.getUserId(), pubId);
    }

    @GetMapping("deleteFavourite")
    public BooleanDTO deleteFavourite(@RequestParam("pubID") int pubId, Principal principal){

        User user = (User) ((Authentication) principal).getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return favService.deleteFavourite((int) userEntity.getUserId(), pubId);
    }
}
