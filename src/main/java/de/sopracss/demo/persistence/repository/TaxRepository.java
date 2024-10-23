package de.sopracss.demo.persistence.repository;

import de.sopracss.demo.persistence.entity.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<TaxEntity, String> {

    Optional<TaxEntity> findTaxEntityByCountry_Name(String countryName);

    Optional<TaxEntity> findByLocaleIgnoreCase(String locale);

    Optional<TaxEntity> findByLocaleContainsIgnoreCase(String locale);
}
