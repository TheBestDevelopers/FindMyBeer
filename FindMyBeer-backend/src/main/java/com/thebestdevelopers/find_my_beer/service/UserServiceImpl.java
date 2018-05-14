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
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula
 */
@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;

    @Override
    public UserDTO getUser(String username, String password) {
        UserEntityF userEntityF = userRepository.findByUsernameAndPassword(username,password).get(0);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userEntityF,UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUser() {
         List<UserEntityF> userEntityFList = userRepository.findAll();
         List<UserDTO> userList = new ArrayList<>();
         for(UserEntityF userEntityF : userEntityFList) {
             userList.add(new UserDTO(userEntityF.getUserId(), userEntityF.getUsername(), userEntityF.getRoleByUserId().getRole()));
         }
         return userList;
    }

    @Override
    public UserDTO createUser(String username, String password, String role) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDao.createUser(username,password,role), UserDTO.class);
    }

    @Override
    public Boolean changeUserPassword(String username, String password, String newPassword){ return userDao.changeUserPassword(username, password, newPassword);}

    @Override
    public Boolean deleteUser(String username, String password) {
        return userDao.deleteUser(username,password);
    }
}
