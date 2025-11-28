package de.sopracss.demo.product.service;

import de.sopracss.demo.persistence.entity.TaxEntity;
import de.sopracss.demo.persistence.repository.TaxRepository;
import de.sopracss.demo.product.model.Product;
import de.sopracss.demo.persistence.entity.ProductEntity;
import de.sopracss.demo.persistence.repository.ProductRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    private final TaxRepository taxRepository;

    public ProductService(ProductRepository productRepository, TaxRepository taxRepository) {
        this.productRepository = productRepository;
        this.taxRepository = taxRepository;
    }

    @Cacheable(cacheNames = "product", key="'AllProducts'")// use "AllProducts" as key instead SimpleKey.EMPTY
    public List<Product> listProducts() {
        TaxEntity tax = taxRepository.findTaxEntityByCountry_Name("Germany").orElse(new TaxEntity());
        log.info("reading products from repository!");
        List<ProductEntity> allProducts = productRepository.findAll();
        return allProducts.stream()
                .peek(productEntity -> productEntity.setVatRate(tax))
                .map(this::mapProduct).toList();
    }

    private Product mapProduct(ProductEntity product) {
        return Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(
                        product.getPrice()
                                .multiply(
                                    BigDecimal.ONE.add(
                                            BigDecimal.valueOf(product.getVatRate().getRateNormal().intValue())
                                                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                                    )
                                ).setScale(2, RoundingMode.HALF_UP)
                )
                .vatRate(product.getVatRate().getRateNormal())
                .build();
    }

    private ProductEntity mapEntity (Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        return entity;
    }

    @CacheEvict(cacheNames ="product", key="'AllProducts'")
    public void addProduct(Product product) {
        productRepository.save(mapEntity(product));
    }

    @CacheEvict(cacheNames = "product", allEntries = true)
    public void deleteProduct(Product product) {
        Optional<ProductEntity> entity = productRepository.findByName(product.getName());
        entity.ifPresent( e -> productRepository.deleteById(e.getId()));
    }
}
