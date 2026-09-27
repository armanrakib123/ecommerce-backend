package com.example.ecommerce.service;

import com.example.ecommerce.category.Category;
import com.example.ecommerce.category.CategoryRepository;
import com.example.ecommerce.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
        ProductRepository productRepository,
        CategoryRepository categoryRepository) {

    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
}
    // Convert Product entity to ProductResponseDTO
    public ProductResponseDTO convertToDTO(Product product) {

        Long categoryId = null;
        String categoryName = null;

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
            categoryName = product.getCategory().getName();
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                categoryId,
                categoryName
        );
    }
    // Create product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    // Create product with category
    public Product createProduct(Product product, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);

        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // Get products with pagination
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    // Search products by name
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    
    }
    // Filter products by price range
    public List<Product> filterProductsByPrice(
        java.math.BigDecimal minPrice,
        java.math.BigDecimal maxPrice) {

         return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    // Get products by category
    public List<Product> getProductsByCategory(Long categoryId) {
            return productRepository.findByCategoryId(categoryId);
    }
    // Get products that are in stock
    public List<Product> getInStockProducts() {
             return productRepository.findByStockGreaterThan(0);
}

    // Get product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Update product
    public Product updateProduct(Long id, Product productDetails) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setStock(productDetails.getStock());
        existingProduct.setCategory(productDetails.getCategory());

        return productRepository.save(existingProduct);
    }

    // Delete product
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}