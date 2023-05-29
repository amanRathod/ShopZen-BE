package com.ecommerce.ShopZenbe.models.address;

import com.ecommerce.ShopZenbe.common.exceptions.RequestValidationException;
import com.ecommerce.ShopZenbe.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ShopZenbe.models.address.dto.AddressDTO;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    public List<AddressDTO> getAllAddresses(UUID customerId) {
       List<Address> addresses = addressRepository.findAllByCustomerId(customerId);
       return addresses.stream()
               .map(address -> modelMapper.map(address, AddressDTO.class))
               .collect(java.util.stream.Collectors.toList());
    }

    public AddressDTO updateAddress(UUID addressId, AddressDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Address address = addressRepository.findById(addressId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Address Not Found", new Throwable("Address does not exist in the system!")));

        if (!address.getCustomer().getEmail().equals(email)) {
            throw new RequestValidationException("You are not authorized to update this address!");
        }

        boolean changes = false;    

        if (dto.getStreet() != null && !dto.getStreet().equals(address.getStreet())) {
            address.setStreet(dto.getStreet());
            changes = true;
        }

        if (dto.getFullName() != null && !dto.getFullName().equals(address.getFullName())) {
            address.setFullName(dto.getFullName());
            changes = true;
        }

        if (dto.getPhone() != null && !dto.getPhone().equals(address.getPhone())) {
            address.setPhone(dto.getPhone());
            changes = true;
        }

        if (dto.getCity() != null && !dto.getCity().equals(address.getCity())) {
            address.setCity(dto.getCity());
            changes = true;
        }

        if (dto.getState() != null && !dto.getState().equals(address.getState())) {
            address.setState(dto.getState());
            changes = true;
        }

        if (dto.getCountry() != null && !dto.getCountry().equals(address.getCountry())) {
            address.setCountry(dto.getCountry());
            changes = true;
        }

        if (dto.getZipCode() != null && !dto.getZipCode().equals(address.getZipCode())) {
            address.setZipCode(dto.getZipCode());
            changes = true;
        }

        if (changes) {
            addressRepository.save(address);
        }

        return modelMapper.map(address, AddressDTO.class);
    }

    public AddressDTO addAddress(AddressDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer Not Found", new Throwable("Customer does not exist in the system!")));

        Address savedAddress = Address.builder()
                .street(dto.getStreet())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .isBilling(false)
                .isShipping(true)
                .customer(customer)
                .build();

        Address address = addressRepository.save(savedAddress);
        return modelMapper.map(address, AddressDTO.class);
    }
}
