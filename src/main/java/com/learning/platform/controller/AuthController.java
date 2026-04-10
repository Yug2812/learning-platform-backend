package com.learning.platform.controller;

import com.learning.platform.dto.ApiResponse;
import com.learning.platform.dto.request.LoginRequest;
import com.learning.platform.dto.request.SignupRequest;
import com.learning.platform.dto.response.JwtResponse;
import com.learning.platform.model.ERole;
import com.learning.platform.model.Role;
import com.learning.platform.model.User;
import com.learning.platform.repository.RoleRepository;
import com.learning.platform.repository.UserRepository;
import com.learning.platform.model.ActivityLog;
import com.learning.platform.repository.ActivityLogRepository;
import com.learning.platform.model.Faculty;
import com.learning.platform.repository.FacultyRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.BadCredentialsException;
import com.learning.platform.security.jwt.JwtUtils;
import com.learning.platform.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ActivityLogRepository activityLogRepository;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
        if (user != null && !user.isAccountNonLocked()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Account locked due to too many failed attempts.", null));
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            if (user != null) {
                user.setFailedAttemptCount(user.getFailedAttemptCount() + 1);
                if (user.getFailedAttemptCount() >= 5) {
                    user.setAccountNonLocked(false);
                }
                userRepository.save(user);

                ActivityLog log = new ActivityLog();
                log.setUser(user);
                log.setActivityType("LOGIN_FAILED");
                log.setDetails("Failed login attempt");
                log.setIpAddress(request.getRemoteAddr());
                activityLogRepository.save(log);
            }
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", "Invalid credentials.", null));
        }

        if (user != null) {
            user.setFailedAttemptCount(0);
            userRepository.save(user);

            ActivityLog log = new ActivityLog();
            log.setUser(user);
            log.setActivityType("LOGIN_SUCCESS");
            log.setDetails("Successful login");
            log.setIpAddress(request.getRemoteAddr());
            activityLogRepository.save(log);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getName(),
                userDetails.getEmail(),
                roles);

        return ResponseEntity.ok(new ApiResponse<>("success", "Login successful", jwtResponse));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<>("error", "Error: Email is already in use!", null));
        }

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (role.equals("admin")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if (role.equals("teacher")) {
                    Role teacherRole = roleRepository.findByName(ERole.ROLE_TEACHER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(teacherRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        if (roles.stream().anyMatch(r -> r.getName().equals(ERole.ROLE_TEACHER))) {
            Faculty faculty = new Faculty();
            faculty.setUser(user);
            faculty.setName(user.getName());
            faculty.setEmail(user.getEmail());
            facultyRepository.save(faculty);
        }

        return ResponseEntity.ok(new ApiResponse<>("success", "User registered successfully!", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> getCurrentUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);
        return ResponseEntity.ok(new ApiResponse<>("success", "User fetched", user));
    }
}
