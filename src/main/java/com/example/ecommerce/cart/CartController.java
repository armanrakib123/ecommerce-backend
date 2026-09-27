package com.example.ecommerce.cart;

import com.example.ecommerce.dto.CartItemResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCart(
            Authentication authentication) {

        Long userId = cartService.getUserIdByEmail(
                authentication.getName());

        List<CartItemResponseDTO> items =
                cartService.getCartItems(userId)
                        .stream()
                        .map(cartService::convertToDTO)
                        .toList();

        return ResponseEntity.ok(items);
    }

    @PostMapping("/add/{productId}")
    public ResponseEntity<CartItemResponseDTO> addToCart(
            @PathVariable Long productId,
            @RequestParam Integer quantity,
            Authentication authentication) {

        Long userId = cartService.getUserIdByEmail(
                authentication.getName());

        CartItem cartItem = cartService.addToCart(
                userId,
                productId,
                quantity);

        return ResponseEntity.ok(
                cartService.convertToDTO(cartItem)
        );
    }

    @PutMapping("/item/{itemId}")
    public ResponseEntity<CartItemResponseDTO> updateQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity,
            Authentication authentication) {

        Long userId = cartService.getUserIdByEmail(
                authentication.getName());

        CartItem cartItem = cartService.updateQuantity(
                userId,
                itemId,
                quantity);

        return ResponseEntity.ok(
                cartService.convertToDTO(cartItem)
        );
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable Long itemId,
            Authentication authentication) {

        Long userId = cartService.getUserIdByEmail(
                authentication.getName());

        cartService.removeFromCart(userId, itemId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            Authentication authentication) {

        Long userId = cartService.getUserIdByEmail(
                authentication.getName());

        cartService.clearCart(userId);

        return ResponseEntity.noContent().build();
    }
}