package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.GetFavouritesDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavouritiesDTO;
import com.thebestdevelopers.find_my_beer.controller.favouritiesControllerParam.AddFavouritesParam;
import com.thebestdevelopers.find_my_beer.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("add")
    public FavouritiesDTO addFavourite(@Valid @RequestBody AddFavouritesParam param) {
        return favService.addFavourite(param.getClientId(),param.getPubId());
    }
}
