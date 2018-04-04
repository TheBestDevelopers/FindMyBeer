package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Wiktor Florencki
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
