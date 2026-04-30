package com.project.fitness.controller;

import com.project.fitness.Model.User;
import com.project.fitness.Security.JwtUtils;
import com.project.fitness.dtos.LoginRequest;
import com.project.fitness.dtos.LoginResponse;
import com.project.fitness.dtos.RegisterRequest;
import com.project.fitness.dtos.UserResponse;
import com.project.fitness.repository.UserRepository;
import com.project.fitness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        try {
            User user = userRepository.findByEmail(loginRequest.getEmail());

            if(user == null) return ResponseEntity.status(401).build();

            if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
                    return ResponseEntity.status(401).build();

            String token = jwtUtils.generateToken(user.getId(), user.getRole().name());

            return ResponseEntity.ok(new LoginResponse(
                    token, userService.mapToResponse(user)
            )
            );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }

    }
}


// USER CREATION AND LOGIN