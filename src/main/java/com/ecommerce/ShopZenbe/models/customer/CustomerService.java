package com.ecommerce.ShopZenbe.models.customer;

import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.common.exceptions.RequestValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CustomerResponseDTO getCustomer(UUID customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer with id %s not found".formatted(customerId)));

        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO addCustomer(CustomerDTO request) {
        String email = request.getEmail();
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "Email already taken!"
            );
        }

        Customer savedCustomer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        Customer customer = customerRepository.save(savedCustomer);
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO updateCustomer(UUID customerId, CustomerUpdateRequest updateRequest) {
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
                        "Email already taken!"
                );
            }

            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("No data changes found!");
        }

        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerResponseDTO.class);
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
