package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.RoleEntity;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.RoleRepository;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dominik Florencki
 */
@Service
public class UserDaoImpl implements UserDao{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserEntity createUser(UserEntity user, String role) {
        UserEntity userEntity = userRepository.save(user);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        roleEntity.setUserId(userEntity.getUserId());
        roleEntity = roleRepository.save(roleEntity);
        roleEntity.setUserByUserId(userEntity);
        userEntity.setRoleByUserId(roleEntity);
        return userEntity;
    }

}
