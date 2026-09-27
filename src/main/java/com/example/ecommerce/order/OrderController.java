package com.example.ecommerce.order;

import com.example.ecommerce.dto.OrderItemResponseDTO;
import com.example.ecommerce.dto.OrderResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(
            Authentication authentication) {

        Long userId = orderService.getUserIdByEmail(
                authentication.getName());

        Order order = orderService.placeOrder(userId);

        return ResponseEntity.ok(
                orderService.convertToDTO(order)
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(
            Authentication authentication) {

        Long userId = orderService.getUserIdByEmail(
                authentication.getName());

        List<OrderResponseDTO> orders =
                orderService.getUserOrders(userId)
                        .stream()
                        .map(orderService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponseDTO>> getOrderItems(
            @PathVariable Long orderId,
            Authentication authentication) {

        Long userId = orderService.getUserIdByEmail(
                authentication.getName());

        List<OrderItemResponseDTO> items =
                orderService.getOrderItems(userId, orderId)
                        .stream()
                        .map(orderService::convertItemToDTO)
                        .toList();

        return ResponseEntity.ok(items);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication) {

        Long userId = orderService.getUserIdByEmail(
                authentication.getName());

        Order order = orderService.cancelOrder(userId, orderId);

        return ResponseEntity.ok(
                orderService.convertToDTO(order)
        );
    }
     @GetMapping("/admin")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {

        List<OrderResponseDTO> orders =
                orderService.getAllOrders()
                        .stream()
                        .map(orderService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(orders);
    }
    @PutMapping("/admin/{orderId}/status")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        Order order = orderService.updateOrderStatus(
                orderId,
                status.toUpperCase());

        return ResponseEntity.ok(
                orderService.convertToDTO(order));
    }
}