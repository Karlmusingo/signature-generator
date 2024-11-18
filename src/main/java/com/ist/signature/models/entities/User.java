package com.ist.signature.models.entities;

import com.ist.signature.models.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

    @NotBlank
    @Email
    @Column(unique = true)
   private String email;

    @NotBlank
   private String password;

   private String phoneNumber;

   private String firstName;

   private String lastName;

   private String title;

   private boolean verified;

   private String signatureUrl;

   @Enumerated(EnumType.STRING)
   private Role role = Role.USER;

   private LocalDateTime createdAt;

   private LocalDateTime updatedAt;

   @PrePersist
    protected void onCreate() {
       createdAt = LocalDateTime.now();
       updatedAt = LocalDateTime.now();
   }

   @PreUpdate
    protected void onUpdate() {
       updatedAt = LocalDateTime.now();
   }
}
