package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.PubDao;

import com.thebestdevelopers.find_my_beer.DTO.PubDTO;

import com.thebestdevelopers.find_my_beer.model.AddressesEntity;
import com.thebestdevelopers.find_my_beer.model.PubEntity;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.AddressRepository;
import com.thebestdevelopers.find_my_beer.repository.PubRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jakub Pisula
 */
@Service
public class PubServiceImpl implements PubService {

    @Autowired
    PubDao pubDao;

    @Autowired
    PubRepository pubRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public PubDTO getPub(String username, String password) {
        return null;
    }

    @Override
    public List<PubDTO> getAllPubs() {
        return null;
    }

    @Override
    public PubDTO createPub(String pubName) {

        ModelMapper mapper = new ModelMapper();
        return mapper.map(pubDao.createPub(pubName), PubDTO.class);
    }

    @Override
    public Boolean changePubPassword(String username, String password, String newPassword) {
        return null;
    }

    @Override
    public Boolean deletePub(String username, String password) {
        return null;
    }

    @Override
    public PubDTO getPubInfo(int userId, int pubId){
        PubEntity pubEntity = pubRepository.findByPubId(pubId).get(0);
        AddressesEntity addressesEntity = addressRepository.findByPubId(pubId).get(0);



        PubDTO pubDTO =new PubDTO();
        return pubDTO;
    }
}