package com.ecommerce.ShopZenbe.models.customer;

import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.common.exceptions.RequestValidationException;
import com.ecommerce.ShopZenbe.models.address.Address;
import com.ecommerce.ShopZenbe.models.address.dto.AddressDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerResponseDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.UpdateCustomerDTO;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer Not Found", new Throwable("Customer does not exist in the system!")));

        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO addCustomer(CustomerDTO customerDto) {
        String email = customerDto.getEmail();
        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateResourceException(
                    "Email already taken!", new Throwable("Please try with another email!")
            );
        }

        Customer savedCustomer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .email(customerDto.getEmail())
                .build();

        Customer customer = customerRepository.save(savedCustomer);
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public CustomerResponseDTO updateCustomer(UUID customerId, UpdateCustomerDTO dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer Not Found", new Throwable("Customer does not exist in the system!")
                ));

        boolean changes = false;

        if (dto.firstName() != null && !dto.firstName().equals(customer.getFirstName())) {
            customer.setFirstName(dto.firstName());
            changes = true;
        }

        if (dto.lastName() != null && !dto.lastName().equals(customer.getLastName())) {
            customer.setLastName(dto.lastName());
            changes = true;
        }

        if (dto.email() != null && !dto.email().equals(customer.getEmail())) {
            if(customerRepository.existsByEmail(dto.email())) {
                throw new DuplicateResourceException(
                        "Email already taken!", new Throwable("Please try with another email!")
                );
            }

            customer.setEmail(dto.email());
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

    public CustomerResponseDTO getCustomerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer Not Found", new Throwable("Customer does not exist in the system!")));

        CustomerResponseDTO responseDTO = modelMapper.map(customer, CustomerResponseDTO.class);
        responseDTO.setAddresses(customer.getAddresses().stream()
            .filter(Address::getIsShipping)
            .map(address -> modelMapper.map(address, AddressDTO.class))
            .collect(Collectors.toList()));

        return responseDTO;
    }

    // TODO: Get customer addresses
    // public List<Address> getCustomerAddresses() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     String email = authentication.getName();

    //     Customer customer = customerRepository.findByEmail(email)
    //             .orElseThrow(() -> new ResourceNotFoundException(
    //                     "Customer Not Found", new Throwable("Customer does not exist in the system!")));

    //     return customer.getAddresses();
    // }
}
