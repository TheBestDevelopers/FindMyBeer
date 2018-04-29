package com.thebestdevelopers.find_my_beer.repository;


import com.thebestdevelopers.find_my_beer.model.UserEntityF;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Dominik Florencki
 */
public interface UserRepository extends JpaRepository<UserEntityF, Long> {
    List<UserEntityF> findByUsernameAndPassword(String username, String password);
}
