package com.example.ecommerce.payments;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.order.Order;
import com.example.ecommerce.order.OrderRepository;
import com.example.ecommerce.user.User;
import com.example.ecommerce.user.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public Payment makePayment(
            Long userId,
            Long orderId,
            String paymentMethod) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        // User can only pay for their own order
        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found");
        }

        // Validate payment method
        if (!paymentMethod.equalsIgnoreCase("UPI")
                && !paymentMethod.equalsIgnoreCase("CARD")
                && !paymentMethod.equalsIgnoreCase("COD")) {

            throw new IllegalArgumentException(
                    "Invalid payment method. Use UPI, CARD, or COD");
        }

        // Prevent duplicate payment
        if (paymentRepository.findByOrderId(orderId).isPresent()) {
            throw new IllegalArgumentException(
                    "Payment already exists for this order");
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod.toUpperCase());
        payment.setStatus("SUCCESS");
        payment.setCreatedAt(LocalDateTime.now());

        // Mark order as paid
        order.setStatus("PAID");
        orderRepository.save(order);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentByOrderId(
            Long userId,
            Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        // User can only view their own order payment
        if (!order.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Payment not found");
        }

        return paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Payment not found"));
    }

    public Long getUserIdByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return user.getId();
    }
}