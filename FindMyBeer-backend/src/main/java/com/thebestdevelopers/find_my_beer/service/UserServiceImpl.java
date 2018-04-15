package com.thebestdevelopers.find_my_beer.service;

import com.thebestdevelopers.find_my_beer.DAO.UserDao;
import com.thebestdevelopers.find_my_beer.DTO.UserDTO;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.hibernate.Criteria;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiktor Florencki
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
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username,password).get(0);
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userEntity,UserDTO.class);
    }

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
    public UserDTO createUser(String username, String password, String role) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(userDao.createUser(username,password,role), UserDTO.class);
    }

    @Override
    public Boolean deleteUser(String username, String password) {
        return userDao.deleteUser(username,password);
    }
}
