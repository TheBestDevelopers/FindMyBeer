package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.ConveniencesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Grzegorz Nowak
 *
 */
public interface ConvenienceRepository extends JpaRepository<ConveniencesEntity, Long> {
    List<ConveniencesEntity> findByPubId(int pubId);
    List<ConveniencesEntity> findByConvenienceTypesIdAndPubId(int convenienceTypesId, int pubId);
    void deleteByConvenienceId(int convenienceId);


}
