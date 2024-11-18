package com.ist.signature.repositories;

import com.ist.signature.models.entities.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldGetUserByEmail() throws Exception {
        // given
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);

        userRepository.save(user);

        // when
        Optional<User> expectedUser = userRepository.findByEmail(email);

        // then
        Assertions.assertThat(expectedUser.isPresent()).isTrue();
    }

    @Test
    void itShouldNotGetUserWhenEmailDoesNotExist() throws Exception {
        // given
        String email = "test@test.com";

        // when
        Optional<User> expectedUser = userRepository.findByEmail(email);

        // then
        Assertions.assertThat(expectedUser.isPresent()).isFalse();
    }

    @Test
    void itShouldReturnTrueWhenEmailExit() throws Exception {
        // given
        String email = "test@test.com";
        User user = new User();
        user.setEmail(email);

        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByEmail(email);

        // then
        Assertions.assertThat(exists).isTrue();
    }


    @Test
    void itShouldReturnFalseWhenEmailDoesNotExit() throws Exception {
        // given
        String email = "test1@test.com";

        // when
        boolean exists = userRepository.existsByEmail(email);

        // then
        Assertions.assertThat(exists).isFalse();
    }
}
