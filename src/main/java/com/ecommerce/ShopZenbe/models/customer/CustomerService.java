package com.ecommerce.ShopZenbe.models.customer;

import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.common.exceptions.RequestValidationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public void addCustomer(CustomerDTO request) {
        String email = request.getEmail();
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "email already taken"
            );
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        customerRepository.save(customer);
    }

    public void updateCustomer(UUID customerId, CustomerUpdateRequest updateRequest) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
                ));

        boolean changes = false;

        if (updateRequest.firstName() != null && !updateRequest.firstName().equals(customer.getFirstName())) {
            customer.setFirstName(updateRequest.firstName());
            changes = true;
        }

        if (updateRequest.lastName() != null && !updateRequest.lastName().equals(customer.getLastName())) {
            customer.setLastName(updateRequest.lastName());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if(customerRepository.existsByEmail(updateRequest.email())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }

            customer.setLastName(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no data changes found");
        }

        customerRepository.save(customer);
    }

    public void deleteCustomerById(UUID customerId) {
        try {
            customerRepository.deleteById(customerId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ResourceNotFoundException(
                    "Customer with ID %s not found".formatted(customerId));
        }
    }
}
