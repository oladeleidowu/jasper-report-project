package com.bimcode.jasperreports.entity;

import com.bimcode.jasperreports.enumeration.ProductType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Column(name = "price")
    private BigDecimal price;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    public Product(String name,
                   String description,
                   ProductType productType,
                   BigDecimal price,
                   LocalDate createdAt) {
        this.name = name;
        this.description = description;
        this.productType = productType;
        this.price = price;
        this.createdAt = createdAt;
    }
}
