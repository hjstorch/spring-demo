package de.sopracss.demo.persistence.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.math.BigDecimal;

import java.util.UUID;

@Data
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    private UUID id;

    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal price;

    @ManyToOne
    private TaxEntity vatRate;
}
