package com.bimcode.jasperreports.repository;

import com.bimcode.jasperreports.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCreatedAt(LocalDate localDate);
}
