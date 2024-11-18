package com.ist.signature.controller;

import com.ist.signature.dtos.request.ProfileRequest;
import com.ist.signature.dtos.response.UserResponse;
import com.ist.signature.exceptions.CustomException;
import com.ist.signature.models.entities.CompanyDetails;
import com.ist.signature.models.enums.Role;
import com.ist.signature.services.CompanyService;
import com.ist.signature.services.UserService;
import com.ist.signature.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.COMPANY_PATH)
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/")
    public ResponseEntity<CompanyDetails> getCompanyDetails() {
        return ResponseEntity.ok(companyService.getCompanyDetails());
    }

    @PutMapping("/")
    public ResponseEntity<CompanyDetails> updateProfile(@Valid @RequestBody CompanyDetails data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        String role = "";

        for (GrantedAuthority grantedAuthority : currentUser.getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        if(!role.equals("ADMIN")){
            throw new CustomException(HttpStatus.FORBIDDEN, "Only the admin can update company details!");
        }

        return ResponseEntity.ok(companyService.updateCompanyDetails(data));
    }

}
