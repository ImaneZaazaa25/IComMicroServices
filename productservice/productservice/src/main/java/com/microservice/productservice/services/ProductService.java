package com.microservice.productservice.services;

import com.microservice.productservice.model.Product;
import com.microservice.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(Product productRequest) {
        Product p = Product.builder().
                name(productRequest.getName()).description(productRequest.getDescription()).price(productRequest.getPrice()).build();
        productRepository.save(p);
        log.info("product is saved" + p.getId());

    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductById(String Id){
        return productRepository.findById(Id).orElseThrow(() -> new RuntimeException("Product not found with id " + Id));
    }
}
