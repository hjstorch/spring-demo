package de.sopracss.demo.product.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Builder
public class Product {

    private String name;

    private String description;

    private BigDecimal price;

    private BigInteger vatRate;
}
