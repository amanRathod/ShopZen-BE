package com.ecommerce.ShopZenbe.auth;

import com.ecommerce.ShopZenbe.common.utils.ApiResponse;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;


    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) throws MessagingException, TemplateException, IOException {
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
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPassword request) throws MessagingException, TemplateException, IOException {
        authenticationService.forgotPassword(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>(201, "Password reset link has been sent to your email");
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword request) throws MessagingException, TemplateException, IOException {
        authenticationService.resetPassword(request);
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse<>(201, "Password has been reset successfully!");
        return ResponseEntity.ok().body(apiResponse);
    }
}
