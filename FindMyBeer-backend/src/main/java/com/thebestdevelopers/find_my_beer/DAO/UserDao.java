package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.UserEntity;
import org.springframework.stereotype.Repository;


/**
 * @author Dominik Florencki
 * Modyfikacje: Jakub Pisula
 */
@Repository
public interface UserDao{
    UserEntity createUser(String username, String password, String role);
    Boolean changeUserPassword(String username, String password, String newPassword);
    Boolean deleteUser(String username, String password);
}
