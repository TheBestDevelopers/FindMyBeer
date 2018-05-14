package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.UserDao;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.model.UserEntityF;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jakub Pisula
 */
@Service
public class PubServiceImpl implements PubService {

    @Override
    public UserDTO getPub(String username, String password) {
        return null;
    }

    @Override
    public List<UserDTO> getAllPubs() {
        return null;
    }

    @Override
    public UserDTO createPub(String username, String password, String role) {
        return null;
    }

    @Override
    public Boolean changePubPassword(String username, String password, String newPassword) {
        return null;
    }

    @Override
    public Boolean deletePub(String username, String password) {
        return null;
    }
}