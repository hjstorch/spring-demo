package de.sopracss.demo.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Data
@Entity
@Table(name = "TAX")
@Document(collection = "tax")
public class TaxEntity {

    @Id
    String locale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COUNTRY_ID")
    CountryEntity country;

    BigInteger rateReduced;
    BigInteger rateNormal;
}
