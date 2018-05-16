package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.FavouritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Jakub Pisula
 */
public interface FavouritesRepository extends JpaRepository<FavouritiesEntity, Integer> {
    List<FavouritiesEntity> findFavouritesEntityByClientId(int clientId);
}
