package com.microservice.productservice.controller;


import com.microservice.productservice.model.Product;
import com.microservice.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
@ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product productRequest){
    productService.createProduct(productRequest);
}

@GetMapping
@ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
    return productService.getAllProducts();
}
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }
}
