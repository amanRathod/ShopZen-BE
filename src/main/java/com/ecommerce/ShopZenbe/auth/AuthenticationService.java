package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.common.enums.Role;
import com.ecommerce.ShopZenbe.common.exceptions.DuplicateResourceException;
import com.ecommerce.ShopZenbe.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ShopZenbe.config.JwtService;
import com.ecommerce.ShopZenbe.mails.MailService;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import com.ecommerce.ShopZenbe.models.customer.dto.CustomerResponseDTO;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.lang.String;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final MailService mailService;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException, TemplateException, IOException {
        boolean userExists = userRepository.existsByEmail(request.getEmail());
        if (userExists) throw new DuplicateResourceException("Email already taken!", new Throwable("Please try with another email!"));

        Customer user = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.CUSTOMER)
                .build();

        Customer savedUser = userRepository.save(user);
        String token = jwtService.generateToken(user);
        mailService.sendWelcomeEmail(user);

        CustomerResponseDTO customerDTO = modelMapper.map(savedUser, CustomerResponseDTO.class);
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

        CustomerResponseDTO customerDTO = modelMapper.map(principal, CustomerResponseDTO.class);
        return new AuthenticationResponse(jwtToken, customerDTO);
    }

    public void forgotPassword(ForgotPassword request) throws MessagingException, TemplateException, IOException {
        Customer user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", new Throwable("User does not exists in the system!")));

        String resetLink = generateResetLink(user);
        mailService.sendResetPasswordEmail(resetLink, user);
    }

    public void resetPassword(ResetPassword request) throws MessagingException, TemplateException, IOException {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(request.getToken()))) {
            throw new ResourceNotFoundException("Invalid or expired token", new Throwable("Please try to reset password again!"));
        }

        String email = (String) redisTemplate.opsForValue().get(request.getToken());
        Customer user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found", new Throwable("User does not exist in the system!")));

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        redisTemplate.delete(request.getToken());
        mailService.sendPasswordResetConfirmationEmail(user);
    }

    private String generateResetLink(Customer user) {
        String token = UUID.randomUUID().toString();
        String resetLink = frontendUrl + "/reset-password/" + token;

        redisTemplate.opsForValue().set(token, user.getEmail(), Duration.ofHours(1));
        return resetLink;
    }
}
