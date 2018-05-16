package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.RoleEntityF;
import com.thebestdevelopers.find_my_beer.model.UserEntityF;
import com.thebestdevelopers.find_my_beer.repository.RoleRepository;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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



    public static String hashPassword(String password_plaintext) {
        String salt = BCrypt.gensalt(12);
        String hashed_password = BCrypt.hashpw(password_plaintext, salt);

        return(hashed_password);
    }

    @Override
    public UserEntityF createUser(String username, String password, String role) {

        UserEntityF userEntityF = new UserEntityF();
        userEntityF.setUsername(username);
        userEntityF.setPassword(hashPassword(password));

        userEntityF = userRepository.save(userEntityF);

        RoleEntityF roleEntityF = new RoleEntityF();
        roleEntityF.setRole(role);
        roleEntityF.setUserId(userEntityF.getUserId());
        roleEntityF = roleRepository.save(roleEntityF);
        roleEntityF.setUserByUserId(userEntityF);
        userEntityF.setRoleByUserId(roleEntityF);
        return userEntityF;
    }

    @Override
    public Boolean changeUserPassword(String username, String password, String newPassword) {
        UserEntityF userEntityF = userRepository.findByUsernameAndPassword(username,password).get(0);
        if(hashPassword(userEntityF.getPassword()).equals(hashPassword(password))) {
            userEntityF.setPassword(hashPassword(newPassword));
            userRepository.save(userEntityF);
            return true;
        } else return false;

    }

    @Override
    public Boolean deleteUser(String username, String password) {
        UserEntityF userEntityF = userRepository.findByUsernameAndPassword(username,password).get(0);
        roleRepository.deleteById(userEntityF.getUserId());
        userRepository.deleteById(userEntityF.getUserId());
        return true;
    }
}
