package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Grzegorz Nowak
 */
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    List<MenuEntity> findByPubId(int pubId);
}
