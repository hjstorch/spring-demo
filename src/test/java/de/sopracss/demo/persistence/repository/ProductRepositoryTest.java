package de.sopracss.demo.persistence.repository;

import de.sopracss.demo.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
    properties = {
            "spring.datasource.url=jdbc:h2:mem:test",
            "spring.jpa.hibernate.ddl-auto=create-drop",
            "demo.cache.redis.cache.enabled=true",
            "spring.cache.redis.time-to-live-minutes=60"
    }
)
@Sql(scripts = {"classpath:testsql/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("unittest")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

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
