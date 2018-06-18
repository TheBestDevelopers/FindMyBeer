package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DTO.BooleanDTO;
import com.thebestdevelopers.find_my_beer.DTO.Favourities.FavResult;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

/**
 * @author Jakub Pisula
 */
public interface FavouritesService {
    @PreAuthorize("hasAnyAuthority('admin','client')")
    List<FavResult> getFavouritePubs(int userId) throws IOException;
    @PreAuthorize("hasAnyAuthority('admin','client')")
    BooleanDTO addFavourite(int userId, int pubId);
    @PreAuthorize("hasAnyAuthority('admin','client')")
    BooleanDTO deleteFavourite(int userId, int pubId);
}
