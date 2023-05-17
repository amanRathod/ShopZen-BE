package com.ecommerce.ShopZenbe.models.customer;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.mails.MailService;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerResponseDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.UpdateCustomerDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    private MailService mailService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/welcome-email")
    public ResponseEntity<Void> sendWelcomeEmail(@RequestParam String email, @RequestParam String name) {
        try {
            mailService.sendWelcomeEmail(email, name);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("profile")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomerProfile() {
        CustomerResponseDTO customer = customerService.getCustomerProfile();

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "", customer, "profile");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers() {
        List<CustomerResponseDTO> customer = (List<CustomerResponseDTO>) customerService.getAllCustomers();

        ApiResponse<List<CustomerResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), "", customer, "customers");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomer(
            @PathVariable("customerId") UUID customerId) {
        CustomerResponseDTO customer = customerService.getCustomer(customerId);

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "", customer, "customer");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> registerCustomer(
            @Valid @RequestBody CustomerDTO dto) {
        CustomerResponseDTO customer = customerService.addCustomer(dto);

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Customer created successfully!", customer, "customer");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCustomer(
            @PathVariable("customerId") UUID customerId,
            @Valid @RequestBody UpdateCustomerDTO dto) {
        CustomerResponseDTO customer = customerService.updateCustomer(customerId, dto);

        ApiResponse<CustomerResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Customer updated successfully!", customer, "customer");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteCustomer(
            @PathVariable("customerId") UUID customerId) {
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.ok("Customer deleted successfully!");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}
