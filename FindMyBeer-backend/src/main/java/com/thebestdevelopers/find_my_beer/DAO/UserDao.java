package com.thebestdevelopers.find_my_beer.DAO;

import com.thebestdevelopers.find_my_beer.model.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;


/**
 * @author Dominik Florencki
 */
@Repository
public interface UserDao{
    UserEntity createUser(String username, String password, String role);
    Boolean deleteUser(String username, String password);
}
