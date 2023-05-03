package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.common.enums.Role;
import com.ecommerce.ShopZenbe.config.JwtService;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerDTO;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;

    public AuthenticationResponse register(RegisterRequest request) {
        Customer user = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        Customer savedUser = userRepository.save(user);
        String token = jwtService.generateToken(user);

        CustomerDTO customerDTO = modelMapper.map(savedUser, CustomerDTO.class);
        return new AuthenticationResponse(token, customerDTO);
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Customer principal = (Customer) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(principal);

        CustomerDTO customerDTO = modelMapper.map(principal, CustomerDTO.class);
        return new AuthenticationResponse(jwtToken, customerDTO);
    }
}
