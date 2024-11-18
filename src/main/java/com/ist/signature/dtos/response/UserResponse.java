package com.ist.signature.dtos.response;

import com.ist.signature.models.entities.CompanyDetails;
import com.ist.signature.models.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID id;

    private String email;

    private String phoneNumber;

    private String title;

    private String firstName;

    private String lastName;

    private boolean verified;

    private String signatureUrl;

    private Role role;

    private CompanyDetails companyDetails;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserResponse() {}

    public UserResponse(UUID id, String email, String phoneNumber, String title, String firstName, String lastName, boolean verified,
                        String signatureUrl, Role role, CompanyDetails companyDetails, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.title = title;
        this.firstName= firstName;
        this.lastName = lastName;
        this.verified = verified;
        this.signatureUrl = signatureUrl;
        this.role = role;
        this.companyDetails = companyDetails;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
