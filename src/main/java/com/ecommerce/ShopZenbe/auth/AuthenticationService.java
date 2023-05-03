package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.common.enums.Role;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public String register(RegisterRequest request) {
        var user = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        var savedUser = userRepository.save(user);
        return "Customer created successfully";
    }
}
