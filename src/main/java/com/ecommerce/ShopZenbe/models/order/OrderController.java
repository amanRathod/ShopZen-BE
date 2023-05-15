package com.ecommerce.ShopZenbe.models.order;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        Set<Order> orders = orderService.getAllOrders();
        ApiResponse<Set<Order>> response = new ApiResponse<>(HttpStatus.OK.value() , "success", orders, "orders");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable UUID orderId) {
        Order order = orderService.getOrder(orderId);
        ApiResponse<Order> response = new ApiResponse<>(HttpStatus.OK.value() , "success", order, "order");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
