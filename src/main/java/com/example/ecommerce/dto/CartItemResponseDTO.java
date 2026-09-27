package com.example.ecommerce.dto;

import java.math.BigDecimal;

public class CartItemResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal total;

    public CartItemResponseDTO() {
    }

    public CartItemResponseDTO(
            Long id,
            Long productId,
            String productName,
            BigDecimal price,
            Integer quantity,
            BigDecimal total) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }
}