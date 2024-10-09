package de.sopracss.demo.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "TAX")
public class TaxEntity {

    @Id
    String locale;

    @ManyToOne()
    @JoinColumn(name = "COUNTRY_ID")
    CountryEntity country;

    BigInteger rateReduced;
    BigInteger rateNormal;
}
