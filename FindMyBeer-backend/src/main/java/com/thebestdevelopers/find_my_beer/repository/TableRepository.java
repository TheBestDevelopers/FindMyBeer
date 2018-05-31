package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.TablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Grzegorz Nowak
 *
 */
public interface TableRepository extends JpaRepository<TablesEntity, Long> {

    List<TablesEntity> findByPubId(int pubId);
}
