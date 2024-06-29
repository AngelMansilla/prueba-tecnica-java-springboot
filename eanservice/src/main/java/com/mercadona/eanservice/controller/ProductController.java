package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.dto.ProductDTO;
import com.mercadona.eanservice.model.Product;
import com.mercadona.eanservice.service.EanService;
import com.mercadona.eanservice.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private EanService eanService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.findById(id);
        return productDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ean/{ean}")
    public ResponseEntity<ProductDTO> getProductByEan(@PathVariable String ean) {
        Optional<ProductDTO> productDTO = eanService.findByEan(ean);
        return productDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> product = productService.findByIdRaw(id);
        if (product.isPresent()) {
            Product updatedProduct = product.get();
            updatedProduct.setName(productDetails.getName());
            updatedProduct.setProvider(productDetails.getProvider());
            updatedProduct.setDestination(productDetails.getDestination());
            updatedProduct.setEan(productDetails.getEan());
            Product savedProduct = productService.save(updatedProduct);
            return ResponseEntity.ok(ProductDTO.fromProduct(savedProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
