package com.thebestdevelopers.find_my_beer.repository;

import com.thebestdevelopers.find_my_beer.model.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Grzegorz Nowak
 */
public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {
    ProductsEntity findByProdId(int prodId);
}
