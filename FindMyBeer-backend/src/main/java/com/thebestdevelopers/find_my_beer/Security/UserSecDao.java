package com.thebestdevelopers.find_my_beer.Security;

import com.thebestdevelopers.find_my_beer.model.UserEntity;
import com.thebestdevelopers.find_my_beer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author Dominik Florencki
 */
@Repository
@Transactional
public class UserSecDao {
    @Autowired
    UserRepository userRepository;

    UserEntity getUserByUsername(String username){
        return userRepository.findByUsername(username).get(0);
    }

}
