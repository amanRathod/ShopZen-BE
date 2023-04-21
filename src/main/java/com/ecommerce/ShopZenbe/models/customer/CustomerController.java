package com.ecommerce.ShopZenbe.models.customer;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable UUID id) {
        Optional<Customer> customerDB = service.getUser(id);

        System.out.println("customer data here ========= " + customerDB.toString());
        if (customerDB.isEmpty()) {
            System.out.println("=-=--=-==--==");
            throw new EntityNotFoundException("Customer not found");
        }

        return customerDB;
    }
}
