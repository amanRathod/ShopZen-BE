package com.ecommerce.ShopZenbe.models.Checkout;

import com.ecommerce.ShopZenbe.common.enums.OrderStatus;
import com.ecommerce.ShopZenbe.models.Checkout.dto.PaymentInfo;
import com.ecommerce.ShopZenbe.models.address.Address;
import com.ecommerce.ShopZenbe.models.address.AddressRepository;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import com.ecommerce.ShopZenbe.models.order.Order;
import com.ecommerce.ShopZenbe.models.Checkout.dto.Purchase;
import com.ecommerce.ShopZenbe.models.Checkout.dto.PurchaseResponse;
import com.ecommerce.ShopZenbe.models.orderItem.OrderItem;
import com.ecommerce.ShopZenbe.models.product.ProductRepository;
import com.stripe.model.PaymentIntent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckoutService {
    @Value("${stripe.key.secret}")
    private String secretKey;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        order.setStatus(OrderStatus.CREATED);

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        Map<UUID, Integer> productQuantities = orderItems.stream()
                .collect(Collectors.toMap(OrderItem::getProductId, OrderItem::getQuantity));
        productRepository.updateQuantities(productQuantities);

        Address billingAddress = purchase.getBillingAddress();
        Address shippingAddress = purchase.getShippingAddress();

        if (billingAddress.getId() != null) {
            billingAddress = addressRepository.findById(billingAddress.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Billing address not found"));
        } else {
            billingAddress.setIsBilling(true);
            billingAddress.setIsShipping(false);
        }

        if (shippingAddress.getId() != null) {
            shippingAddress = addressRepository.findById(shippingAddress.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Shipping address not found"));
        } else {
            shippingAddress.setIsBilling(false);
            shippingAddress.setIsShipping(true);
        }

        order.setBillingAddress(billingAddress);
        order.setShippingAddress(shippingAddress);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        Address finalBillingAddress = billingAddress;
        Address finalShippingAddress = shippingAddress;

        customerOptional.ifPresent(customer -> {
            if (finalBillingAddress.getId() == null) finalBillingAddress.setCustomer(customer);
            if (finalShippingAddress.getId() == null) finalShippingAddress.setCustomer(customer);

            customer.setPrimaryAddress(finalShippingAddress);
            customer.add(order);
            customerRepository.save(customer);
        });

        return new PurchaseResponse(orderTrackingNumber);
    }

    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        Stripe.apiKey = secretKey;

        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfo.getAmount());
        params.put("currency", paymentInfo.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);
        params.put("description", "ShopZen Order");
        params.put("receipt_email", paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
