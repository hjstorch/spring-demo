package de.sopracss.demo.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "COUNTRY")
public class CountryEntity {
    @Id
    private String id;

    private String name;
}
