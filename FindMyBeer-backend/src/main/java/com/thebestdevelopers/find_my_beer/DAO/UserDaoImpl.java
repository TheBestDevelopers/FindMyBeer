package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.RoleEntity;
import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.RoleRepository;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula
 */
@Service
public class UserDaoImpl implements UserDao{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


//
//    public static String hashPassword(String password_plaintext) {
//        String salt = BCrypt.gensalt(12);
//        String hashed_password = BCrypt.hashpw(password_plaintext, salt);
//
//        return(hashed_password);
//    }

    @Override
    public UserEntity createUser(String username, String password, String role) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        //userEntity.setPassword(hashPassword(password));
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
    public Boolean changeUserPassword(String username, String password, String newPassword) {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username,password).get(0);
//        if(hashPassword(userEntity.getPassword()).equals(hashPassword(password))) {
//            userEntity.setPassword(hashPassword(newPassword));
//            userRepository.save(userEntity);
            return true;
//        } else return false;

    }

    @Override
    public Boolean deleteUser(String username, String password) {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username,password).get(0);
        roleRepository.deleteById(userEntity.getUserId());
        userRepository.deleteById(userEntity.getUserId());
        return true;
    }
}
