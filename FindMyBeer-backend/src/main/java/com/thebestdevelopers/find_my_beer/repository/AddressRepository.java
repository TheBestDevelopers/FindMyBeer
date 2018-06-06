package com.thebestdevelopers.find_my_beer.repository;


import com.thebestdevelopers.find_my_beer.model.AddressesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
* @author Grzegorz Nowak
 *
 */
public interface AddressRepository extends JpaRepository<AddressesEntity, Long> {

    List<AddressesEntity> findByPubId(int pubId);
    List<AddressesEntity> findAll();
}
