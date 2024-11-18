package com.ist.signature.dtos.response;

import com.ist.signature.models.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
     String token;
     String email;
     Role role;

     public AuthResponse(String token, String email, Role role) {
         this.token = token;
         this.email = email;
         this.role = role;
     }
}
