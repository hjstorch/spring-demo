package de.sopracss.demo.product.service;

import de.sopracss.demo.product.model.Product;
import de.sopracss.demo.persistence.entity.ProductEntity;
import de.sopracss.demo.persistence.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listProducts() {
        List<ProductEntity> allProducts = productRepository.findAll();
        return allProducts.stream().map(this::mapProduct).toList();
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
}
