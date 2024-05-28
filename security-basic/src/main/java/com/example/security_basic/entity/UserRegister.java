package com.example.security_basic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Random;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "emailid_unique",
                columnNames = "email_address"
        )
)
@Entity
@ToString
@NoArgsConstructor
public class UserRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String firstName;
    private String lastName;

    @Column(name = "email_address", nullable = false)
    private String emailId;

    private String role;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

//    @PrePersist
//    public void generateUserId() {
//        if (this.userId == null) {
//            this.userId = UUID.randomUUID();
//        }
//    }
}

