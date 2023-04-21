package com.ecommerce.ShopZenbe.models.customer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Customer>> getCustomerById(@PathVariable UUID id) {
//        Optional<Customer> customerDB = customerService.getUser(id);
//
//        System.out.println("customer data here ========= " + customerDB.toString());
//        if (customerDB.isEmpty()) {
//            System.out.println("=-=--=-==--==");
//            throw new EntityNotFoundException("Customer not found");
//        }
//
//        return ResponseEntity.ok(customerDB);
//    }

    @GetMapping("{customerId}")
    public Optional<Customer> getCustomer(
            @PathVariable("customerId") UUID customerId) {
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public ResponseEntity<?> registerCustomer(
            @Valid @RequestBody CustomerDTO request) {
        customerService.addCustomer(request);
        return ResponseEntity.ok("Customer Added Successfully");
    }

    @PutMapping("{customerId}")
    public void updateCustomer(
            @PathVariable("customerId") UUID customerId,
            @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomer(customerId, updateRequest);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(
            @PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
    }
}
