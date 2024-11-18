package com.ist.signature.services;

import com.ist.signature.dtos.request.ProfileRequest;
import com.ist.signature.dtos.response.UserResponse;
import com.ist.signature.models.entities.User;
import com.ist.signature.repositories.CompanyDetailsRepository;
import com.ist.signature.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private CompanyDetailsRepository companyRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, companyRepository);
    }

    @Test
    void getUserInfo() {
        String email = "test@test.com";

        try{
            Optional<UserResponse> foundUser = Optional.ofNullable(userService.getUserInfo(email));
            Assertions.assertThat(foundUser.isPresent()).isTrue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userRepository).findByEmail(email);
    }

    @Test
    void getAllUsers() {
        // when
        userService.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void updateUser() {
        // given
        User user = new User();
        user.setFirstName("test");

        ProfileRequest data = new ProfileRequest();
        data.setFirstName("test1");
        // when
        userService.updateUser(data, user);
        // then
        verify(userRepository).save(user);
    }
}