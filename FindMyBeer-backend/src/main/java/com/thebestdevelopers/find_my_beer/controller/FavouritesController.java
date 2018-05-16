package com.thebestdevelopers.find_my_beer.controller;

import com.thebestdevelopers.find_my_beer.DTO.FavouritiesDTO;
import com.thebestdevelopers.find_my_beer.controller.favouritiesControllerParam.AddFavouritesParam;
import com.thebestdevelopers.find_my_beer.controller.favouritiesControllerParam.GetFavouritesParam;
import com.thebestdevelopers.find_my_beer.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("")
    public List<FavouritiesDTO> getFavourites(@Valid @RequestBody GetFavouritesParam param){return favService.getFavourites(param.getClientId()); }

    @PostMapping("add")
    public FavouritiesDTO addFavourite(@Valid @RequestBody AddFavouritesParam param) {
        return favService.addFavourite(param.getClientId(),param.getPubId());
    }
}
