package de.sopracss.demo.persistence.repository;

import de.sopracss.demo.persistence.entity.ProductEntity;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Override
    @BatchSize(size = 100)
    List<ProductEntity> findAll();

    Optional<ProductEntity> findByName(String name);
}
