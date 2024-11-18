package com.ist.signature.controller;

import com.ist.signature.dtos.request.ProfileRequest;
import com.ist.signature.dtos.request.SignUpRequest;
import com.ist.signature.dtos.request.UpdateTitleRequest;
import com.ist.signature.dtos.response.UserResponse;
import com.ist.signature.exceptions.CustomException;
import com.ist.signature.services.UserService;
import com.ist.signature.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.USERS_PATH)
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        String email = currentUser.getUsername();

        return ResponseEntity.ok(userService.getUserInfo(email));
    }

    @PutMapping("/")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody ProfileRequest data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        String email = currentUser.getUsername();

        return ResponseEntity.ok(userService.updateUser(data, email));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        String role = "";

        for (GrantedAuthority grantedAuthority : currentUser.getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        if(!role.equals("ADMIN")){
            throw new CustomException(HttpStatus.FORBIDDEN, "Only the admin can see all users!");
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/title/{userId}")
    public ResponseEntity<UserResponse> updateUsersTitle(@PathVariable UUID userId, @Valid @RequestBody UpdateTitleRequest data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        String role = "";

        for (GrantedAuthority grantedAuthority : currentUser.getAuthorities()) {
            role = grantedAuthority.getAuthority();
        }

        if(!role.equals("ADMIN")){
            throw new CustomException(HttpStatus.FORBIDDEN, "Only the admin can edit users title!");
        }

        return ResponseEntity.ok(userService.updateUsersTitle(data, userId));
    }


}
