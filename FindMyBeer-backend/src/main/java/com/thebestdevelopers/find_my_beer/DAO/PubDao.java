package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.PubEntity;
import org.springframework.stereotype.Repository;

/**
 * @author Jakub Pisula
 */
@Repository
public interface PubDao {
    PubEntity createPub(String pubName);
}
