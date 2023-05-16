package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.common.exceptions.ResourceNotFoundException;
import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import com.ecommerce.ShopZenbe.mails.MailService;
import com.ecommerce.ShopZenbe.models.customer.Customer;
import com.ecommerce.ShopZenbe.models.customer.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CustomerRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>(201, "Account registered successfully!", response, "auth");

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, response.accessToken())
                .body(apiResponse);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);

        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>(200, "Logged in successfully!", response, "auth");

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, response.accessToken())
                .body(apiResponse);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPassword request) {
        Customer user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Email Not Found!"));

        String resetLink = generateResetLink(user);

        try {
            mailService.sendResetPasswordEmail(resetLink, user);
            ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>(201, "Password reset link has been sent to your email");
            return ResponseEntity.ok().body(apiResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword request) {
         if (Boolean.FALSE.equals(redisTemplate.hasKey(request.getToken()))) {
             return ResponseEntity.badRequest().build();
         }

        String email = (String) redisTemplate.opsForValue().get(request.getToken());

        var user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        authenticationService.resetPassword(user.get(), request.getPassword());
        redisTemplate.delete(request.getToken());

        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>(201, "Password has been reset successfully!");
        return ResponseEntity.ok().body(apiResponse);
    }

    private String generateResetLink(Customer user) {
        String token = UUID.randomUUID().toString();
        String resetLink = "https://shop-zen-crm.vercel.app/reset-password/" + token;

        redisTemplate.opsForValue().set(token, user.getEmail(), Duration.ofMinutes(1));
        return resetLink;
    }

}
