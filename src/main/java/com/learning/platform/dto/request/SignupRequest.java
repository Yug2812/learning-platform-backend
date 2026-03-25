package com.learning.platform.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;
@Data
public class SignupRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    private Set<String> roles;
    @NotBlank
    private String password;
}
