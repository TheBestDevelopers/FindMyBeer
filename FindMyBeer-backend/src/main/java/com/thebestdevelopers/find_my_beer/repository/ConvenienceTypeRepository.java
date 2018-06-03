package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.ConvenienceTypesEntity;
import com.thebestdevelopers.find_my_beer.model.PubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Grzegorz Nowak
 *
 */
public interface ConvenienceTypeRepository extends JpaRepository<ConvenienceTypesEntity, Long> {
    List<ConvenienceTypesEntity> findByConvenienceTypesId(int convenienceTypesId);
}
