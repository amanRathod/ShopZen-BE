package com.ecommerce.ShopZenbe.models.customer;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getCustomers() {
        List<CustomerResponseDTO> customer = (List<CustomerResponseDTO>) customerService.getAllCustomers();

        ApiResponse<List<CustomerResponseDTO>> response = ApiResponse.<List<CustomerResponseDTO>>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("")
                .data(customer)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{customerId}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomer(
            @PathVariable("customerId") UUID customerId) {
        CustomerResponseDTO customer = customerService.getCustomer(customerId);

        ApiResponse<CustomerResponseDTO> response = ApiResponse.<CustomerResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("")
                .data(customer)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> registerCustomer(
            @Valid @RequestBody CustomerDTO request) {
        CustomerResponseDTO customer = customerService.addCustomer(request);

        ApiResponse<CustomerResponseDTO> response = ApiResponse.<CustomerResponseDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Customer created successfully!")
                .data(customer)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable("customerId") UUID customerId,
            @Valid @RequestBody CustomerUpdateRequest updateRequest) {
        CustomerResponseDTO customer = customerService.updateCustomer(customerId, updateRequest);

        ApiResponse<CustomerResponseDTO> response = ApiResponse.<CustomerResponseDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Customer updated successfully!")
                .data(customer)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<?> deleteCustomer(
            @PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok("Customer deleted successfully!");
    }
}
