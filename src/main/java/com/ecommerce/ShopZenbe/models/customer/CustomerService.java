package com.ecommerce.ShopZenbe.models.customer;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Optional<Customer> getUser(UUID id) {
        return repository.findById(id);
    }
}
