package com.example.olditemtradeplatform.product.repository;

import com.example.olditemtradeplatform.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
