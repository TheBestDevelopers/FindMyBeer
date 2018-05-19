package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dominik Florencki
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
