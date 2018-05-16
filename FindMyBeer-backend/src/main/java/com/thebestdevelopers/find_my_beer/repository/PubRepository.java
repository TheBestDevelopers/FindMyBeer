package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.PubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Jakub Pisula
 */
public interface PubRepository extends JpaRepository<PubEntity, Long> {
}
