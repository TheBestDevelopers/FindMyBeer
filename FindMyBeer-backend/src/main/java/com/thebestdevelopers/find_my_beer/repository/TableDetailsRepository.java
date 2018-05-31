package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.TableDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Grzegorz Nowak
 *
 */
public interface TableDetailsRepository extends JpaRepository<TableDetailsEntity, Long> {
    TableDetailsEntity findByTableId(int tableId);
}
