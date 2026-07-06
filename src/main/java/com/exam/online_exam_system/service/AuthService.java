package com.exam.online_exam_system.service;

import com.exam.online_exam_system.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.exam.online_exam_system.dto.RegisterRequest;
import com.exam.online_exam_system.dto.LoginRequest;

import com.exam.online_exam_system.entity.User;

import com.exam.online_exam_system.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    // REGISTER
    public String register(RegisterRequest request) {

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return "User Registered Successfully";
    }

    // LOGIN
    public String login(LoginRequest request) {

        Optional<User> optionalUser =
                userRepository.findByEmail(
                        request.getEmail()
                );

        // EMAIL CHECK
        if (optionalUser.isEmpty()) {

            throw new RuntimeException(
                    "User Not Found"
            );
        }

        User user = optionalUser.get();

        // PASSWORD CHECK
        boolean isPasswordMatch =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!isPasswordMatch) {

            throw new RuntimeException(
                    "Invalid Password"
            );
        }

        // GENERATE TOKEN
        String token =
                JwtUtil.generateToken(
                        user.getEmail()
                );

        return token
                + "|"
                + user.getEmail()
                + "|"
                + user.getRole()
                + "|"
                + user.getName();
    }
}