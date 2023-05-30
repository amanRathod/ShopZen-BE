package com.ecommerce.ShopZenbe.models.address;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.models.address.dto.AddressDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PatchMapping("{addressId}")
    @PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<AddressDTO>> updateAddress(
            @PathVariable("addressId") UUID addressId, @Valid @RequestBody AddressDTO dto) {
        AddressDTO address = addressService.updateAddress(addressId, dto);

        ApiResponse<AddressDTO> response = new ApiResponse<>(HttpStatus.OK.value(), "Address updated successfully!", address, "address");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressDTO>> addAddress(@Valid @RequestBody AddressDTO dto) {
        AddressDTO address = addressService.addAddress(dto);

        ApiResponse<AddressDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(), "Address added successfully!", address, "address");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
