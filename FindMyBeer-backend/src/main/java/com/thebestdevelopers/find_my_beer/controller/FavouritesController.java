package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;
import com.thebestdevelopers.find_my_beer.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("getFavourites")
    public List<FavResult> getPubs(@RequestParam("userID") int userId, Principal principal) throws IOException {
        //User user = (User) ((Authentication) principal).getPrincipal();
        //UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get(0);

        return favService.getPubs(userId);
    }

    @GetMapping("addFavourite")
    public BooleanDTO addFavourite(@RequestParam("userID") int userId, @RequestParam("pubID") int pubId, Principal principal){
        return favService.addFavourite(userId, pubId);
    }

    @GetMapping("deleteFavourite")
    public BooleanDTO deleteFavourite(@RequestParam("userID") int userId, @RequestParam("pubID") int pubId, Principal principal){
        return favService.deleteFavourite(userId, pubId);
    }
}
