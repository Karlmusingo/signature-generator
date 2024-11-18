package com.ist.signature.services;

import com.ist.signature.dtos.request.ProfileRequest;
import com.ist.signature.dtos.request.UpdateTitleRequest;
import com.ist.signature.dtos.response.UserResponse;
import com.ist.signature.exceptions.CustomException;
import com.ist.signature.models.entities.CompanyDetails;
import com.ist.signature.models.entities.User;
import com.ist.signature.repositories.CompanyDetailsRepository;
import com.ist.signature.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CompanyDetailsRepository companyRepository;

    public UserResponse getUserInfo(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        List<CompanyDetails> cds = companyRepository.findAll();

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        if(!cds.isEmpty()) {
            userResponse.setCompanyDetails(cds.get(0));
        }

        return userResponse;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> userResponses = new ArrayList<>();

        users.forEach(user -> {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);

            userResponses.add(userResponse);
        });

        return userResponses;
    }

    public UserResponse updateUser(ProfileRequest data, String email) {
        User user = userRepository.findByEmail(email).orElseThrow();

        return updateUser(data, user);
    }

    public UserResponse updateUser(ProfileRequest data, User user) {
        Optional.ofNullable(data.getPhoneNumber()).ifPresent(user::setPhoneNumber);
        Optional.ofNullable(data.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(data.getLastName()).ifPresent(user::setLastName);

        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
    }

    public UserResponse updateUsersTitle(UpdateTitleRequest data, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException(HttpStatus.NOT_FOUND, "User not found")
                );
       user.setTitle(data.getTitle());

        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);

        return userResponse;
    }


}
