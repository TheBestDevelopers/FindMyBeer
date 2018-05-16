package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.PubEntity;
import com.thebestdevelopers.find_my_beer.repository.PubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jakub Pisula
 */
@Service
public class PubDaoImpl implements PubDao {

    @Autowired
    PubRepository pubRepository;

    @Override
    public PubEntity createPub(String pubName) {
        PubEntity pubEntity = new PubEntity();
        pubEntity.setPubName(pubName);

        pubEntity = pubRepository.save(pubEntity);
        return pubEntity;


    }
}
