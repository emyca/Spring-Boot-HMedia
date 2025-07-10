package com.example.Spring_Boot_HMedia.repository;

import com.example.Spring_Boot_HMedia.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("UPDATE Product p SET p.stock = p.stock + ?1 WHERE p.id = ?2")
    @Modifying
    @Transactional
    void accept(double quota, long id);

    @Query("UPDATE Product p SET p.stock = p.stock - ?1 WHERE p.id = ?2")
    @Modifying
    @Transactional
    void ship(double quota, long id);
}
