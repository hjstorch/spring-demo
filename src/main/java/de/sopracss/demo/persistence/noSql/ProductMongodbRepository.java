package de.sopracss.demo.persistence.noSql;

import de.sopracss.demo.persistence.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductMongodbRepository extends MongoRepository<ProductEntity, UUID> {

    Optional<ProductEntity> findByName(String name);

}
