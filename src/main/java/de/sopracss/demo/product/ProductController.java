package de.sopracss.demo.product;

import de.sopracss.demo.product.model.Product;
import de.sopracss.demo.product.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> listProducts() {
        return productService.listProducts();
    }
}
