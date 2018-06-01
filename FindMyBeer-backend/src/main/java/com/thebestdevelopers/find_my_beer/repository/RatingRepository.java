package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.RatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Grzegorz Nowak
 */
public interface RatingRepository extends JpaRepository<RatingsEntity, Long> {
    List<RatingsEntity> findByPubId(int pubId);
}
