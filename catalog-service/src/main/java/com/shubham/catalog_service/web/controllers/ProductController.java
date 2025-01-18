package com.shubham.catalog_service.web.controllers;

import com.shubham.catalog_service.domain.PagedResult;
import com.shubham.catalog_service.domain.Product;
import com.shubham.catalog_service.domain.ProductNotFoundException;
import com.shubham.catalog_service.domain.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
        log.info("Fetching products for page: {}", pageNo);
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProduct(@PathVariable String code) {
        log.info("Fetching product with code: {}", code);
        return productService.getProduct(code).map(ResponseEntity::ok)
                .orElseThrow(() ->ProductNotFoundException.forCode(code));
    }
}
