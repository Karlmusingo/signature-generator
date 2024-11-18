package com.ist.signature.services;

import com.ist.signature.config.SecurityConfig;
import com.ist.signature.dtos.request.LoginRequest;
import com.ist.signature.dtos.request.SignUpRequest;
import com.ist.signature.dtos.response.AuthResponse;
import com.ist.signature.exceptions.CustomException;
import com.ist.signature.models.entities.User;
import com.ist.signature.repositories.UserRepository;
import com.ist.signature.security.JwtTokenProvider;
import com.ist.signature.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordUtils passwordUtils;
    private final EmailService emailService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse signUp(SignUpRequest data) {
        if(userRepository.existsByEmail(data.getEmail())) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Email already exists");
        }

        User user = new User();

        user.setEmail(data.getEmail());
        user.setPassword(passwordUtils.hashPassword(data.getPassword()));
        user.setPhoneNumber(data.getPhoneNumber());

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());
        String verificationToken = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());

        emailService.sendVerificationEmail(savedUser.getEmail(), verificationToken);

        return new AuthResponse(null, user.getEmail(), user.getRole(), user.isVerified());
    }

    public AuthResponse login(LoginRequest data) {
        User user = userRepository.findByEmail(data.getEmail()).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Invalid email or password"));

        if(!passwordUtils.verifyPassword(data.getPassword(), user.getPassword())) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"Wrong password");
        }

        if(!user.isVerified()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,"User is not verified");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getEmail(), user.getRole(), user.isVerified());
    }


    public String verifyEmail(String token) {
        String email = new JwtTokenProvider().getEmailFromToken(token);

        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Email not found"));

        user.setVerified(true);

        userRepository.save(user);

        return "Email verified successfully";
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
