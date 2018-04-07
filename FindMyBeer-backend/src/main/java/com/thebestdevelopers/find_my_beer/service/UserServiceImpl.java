package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.UserDao;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiktor Florencki
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserDao userDao;


    @Override
    public List<UserDTO> getAllUser() {
         List<UserEntity> userEntityList = userRepository.findAll();
         List<UserDTO> userList = new ArrayList<>();
         for(UserEntity userEntity : userEntityList) {
             userList.add(new UserDTO(userEntity.getUserId(), userEntity.getUsername(), userEntity.getRoleByUserId().getRole()));
         }
         return userList;
    }

    @Override
    public UserDTO createUser(UserEntity user, String role) {
        UserEntity userEntity = userDao.createUser(user,role);
        return new UserDTO(userEntity.getUserId(), userEntity.getUsername(), userEntity.getRoleByUserId().getRole());
    }
}
