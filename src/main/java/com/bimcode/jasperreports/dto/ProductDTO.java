package com.bimcode.jasperreports.dto;

import com.bimcode.jasperreports.enumeration.ProductType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private String productType;

    private BigDecimal price;

    private LocalDate createdAt;

}
