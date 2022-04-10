package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.entity.ApplicationUser;
import com.dhinesh.tvservice.repository.ApplicationUserRepository;
import com.dhinesh.tvservice.security.ApplicationUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApplicationUserDetailsServiceTest {

    @Mock private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ApplicationUserDetailsService applicationUserDetailsServiceTest;

    @BeforeEach
    void setUp() {
        applicationUserDetailsServiceTest = new ApplicationUserDetailsService(applicationUserRepository,passwordEncoder);
    }

    @Test
    void loadUserByUsername() {
        String username = "test";
        ApplicationUser user =  new ApplicationUser();
        user.setPassword("test");
        user.setUsername(username);
        user.setRoles("ROLE_USER");

        ApplicationUserDetails applicationUserDetails = new ApplicationUserDetails(user);

        given(applicationUserRepository.findByUsername(anyString())).willReturn(Optional.of(user));

        assertThat(applicationUserDetailsServiceTest.loadUserByUsername(username)).usingRecursiveComparison()
                .isEqualTo(applicationUserDetails);
    }

    @Test
    void loadUserByUsernameNotExists() {

        String username = "test";

        assertThatThrownBy(() -> applicationUserDetailsServiceTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(String.format("Username %s not found",username));
    }
}