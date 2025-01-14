package de.sopracss.demo.persistence.entity;

import jakarta.persistence.*;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

import java.util.UUID;

@Data
@Entity
@Table(name = "PRODUCT")
@Document(collection = "product")
public class ProductEntity {

    @Id
    private UUID id;

    @Column(unique = true)
    private String name;
    private String description;
    private BigDecimal price;

    @Transient
    private TaxEntity vatRate;
}
