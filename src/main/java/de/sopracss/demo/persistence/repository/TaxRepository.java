package de.sopracss.demo.persistence.repository;

import de.sopracss.demo.persistence.entity.TaxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRepository extends JpaRepository<TaxEntity, String> {
}
