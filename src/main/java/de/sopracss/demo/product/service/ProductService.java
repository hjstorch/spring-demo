package de.sopracss.demo.product.service;

import de.sopracss.demo.product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    public List<Product> listProducts() {
        return Collections.emptyList();
    }

    public void addProduct(Product product) {

    }

    public void deleteProduct(Product product) {

    }
}
