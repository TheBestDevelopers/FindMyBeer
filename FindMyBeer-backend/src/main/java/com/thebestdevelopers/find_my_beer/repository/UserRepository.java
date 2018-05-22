package com.thebestdevelopers.find_my_beer.repository;


import com.thebestdevelopers.find_my_beer.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Dominik Florencki
 *
 * Modyfikacje: Grzegorz Nowak - dodano findByUsername - 22.05.2018
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUsernameAndPassword(String username, String password);
    List<UserEntity> findByUsername(String username);
}
