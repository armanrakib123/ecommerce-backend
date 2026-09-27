package com.example.ecommerce.controller;

import com.example.ecommerce.category.Category;
import com.example.ecommerce.category.CategoryRepository;
import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @Valid @RequestBody ProductRequestDTO request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product createdProduct = productService.createProduct(
                product,
                request.getCategoryId()
        );

        return ResponseEntity.ok(
                productService.convertToDTO(createdProduct)
        );
    }
    // Get all products
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sort[0])
        );

        Page<ProductResponseDTO> products =
                productService.getAllProducts(pageable)
                        .map(productService::convertToDTO);

        return ResponseEntity.ok(products);
    }
    // Search products by name
        @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(
            @RequestParam String name) {

        List<ProductResponseDTO> products =
                productService.searchProductsByName(name)
                        .stream()
                        .map(productService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(products);
    }
    // Filter products by price range
        @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProductsByPrice(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {

        List<ProductResponseDTO> products =
                productService.filterProductsByPrice(minPrice, maxPrice)
                        .stream()
                        .map(productService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(products);
    }
    // Get products by category
        @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(
            @PathVariable Long categoryId) {

        List<ProductResponseDTO> products =
                productService.getProductsByCategory(categoryId)
                        .stream()
                        .map(productService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(products);
    }
    // Get products that are in stock
        @GetMapping("/in-stock")
    public ResponseEntity<List<ProductResponseDTO>> getInStockProducts() {

        List<ProductResponseDTO> products =
                productService.getInStockProducts()
                        .stream()
                        .map(productService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(products);
    }

    // Get product by ID
        @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @PathVariable Long id) {

        return productService.getProductById(id)
                .map(productService::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update product
       // Update product with category
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequestDTO request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = productService.updateProduct(
                id,
                product
        );

        return ResponseEntity.ok(
                productService.convertToDTO(updatedProduct)
        );
    }
    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}