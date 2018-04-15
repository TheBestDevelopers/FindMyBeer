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
    public UserEntity createUser(String username, String password, String role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);

        userEntity = userRepository.save(userEntity);

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(role);
        roleEntity.setUserId(userEntity.getUserId());
        roleEntity = roleRepository.save(roleEntity);
        roleEntity.setUserByUserId(userEntity);
        userEntity.setRoleByUserId(roleEntity);
        return userEntity;
    }

    @Override
    public Boolean deleteUser(String username, String password) {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username,password).get(0);
        roleRepository.deleteById(userEntity.getUserId());
        userRepository.deleteById(userEntity.getUserId());
        return true;
    }
}
