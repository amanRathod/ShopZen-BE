package com.ecommerce.ShopZenbe.models.Checkout;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.models.Checkout.dto.Purchase;
import com.ecommerce.ShopZenbe.models.Checkout.dto.PurchaseResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;
    CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

     @PostMapping("/purchase")
    public ResponseEntity<ApiResponse<PurchaseResponse>> placeOrder( @RequestBody Purchase purchase) {
        System.out.println("placeOrder");
         PurchaseResponse purchaseResponse = checkoutService.placeOrder(purchase);

         ApiResponse<PurchaseResponse> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Order placed successfully!", purchaseResponse, "orderTrackingNumber");
         return ResponseEntity.status(HttpStatus.CREATED).body(response);
     }
}
