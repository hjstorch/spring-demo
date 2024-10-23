package de.sopracss.demo.persistence.repository;

import de.sopracss.demo.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
    properties = {
            "spring.datasource.url=jdbc:tc:postgresql:16-alpine://localhost:5432/test",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "spring.datasource.username=test",
            "spring.datasource.password=test",
            "spring.jpa.database=POSTGRESQL"
    }
)
@Sql(scripts = {"classpath:testsql/insert_postgres.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:testsql/cleanup_postgres.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("unittest")
@Testcontainers
class ProductRepositoryTCTest {

    @Autowired
    private ProductRepository repository;

    @Container
    private final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withExposedPorts(5432);

    @Test
    void testReadAll() {
        List<ProductEntity> resultset = repository.findAll();

        assertThat(resultset)
                .hasSize(2)
                .extracting("name").contains("P1", "P2");
    }

    @Test
    void testFindOne() {
        Optional<ProductEntity> result = repository.findByName("P1");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo("P1");
    }
}
