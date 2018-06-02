package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.PersistentLoginsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dominik Florencki
 */
public interface PersistentLoginsRepository extends JpaRepository<PersistentLoginsEntity, Long> {
}
