package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.ConveniencesEntity;
import com.thebestdevelopers.find_my_beer.model.PubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConvenienceRepository extends JpaRepository<PubEntity, Long> {
    List<ConveniencesEntity> findByPubId(int pubId);
}
