package com.ecommerce.ShopZenbe.models.address;

import com.ecommerce.ShopZenbe.models.address.dto.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressService(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }

    public List<AddressDTO> getAllAddresses(UUID customerId) {
       List<Address> addresses = addressRepository.findAllByCustomerId(customerId);
       return addresses.stream()
               .map(address -> modelMapper.map(address, AddressDTO.class))
               .collect(java.util.stream.Collectors.toList());
    }
}
