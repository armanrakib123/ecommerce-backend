package com.example.ecommerce.dto;

import java.math.BigDecimal;

public class OrderItemResponseDTO {

    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;

    public OrderItemResponseDTO() {
    }

    public OrderItemResponseDTO(
            Long id,
            Long productId,
            String productName,
            Integer quantity,
            BigDecimal price,
            BigDecimal total) {

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
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

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotal() {
        return total;
    }
}