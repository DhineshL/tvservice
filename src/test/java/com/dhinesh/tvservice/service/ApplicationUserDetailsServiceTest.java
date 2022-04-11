package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.entity.ApplicationUser;
import com.dhinesh.tvservice.exception.ConflictException;
import com.dhinesh.tvservice.model.UserModel;
import com.dhinesh.tvservice.repository.ApplicationUserRepository;
import com.dhinesh.tvservice.security.ApplicationUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicationUserDetailsServiceTest {

    @Mock private ApplicationUserRepository applicationUserRepository;

    @Mock
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

    @Test
    void createUser() {
        UserModel user = new UserModel();
        user.setUsername("test");
        user.setPassword("test-password");

        ApplicationUser appUser = new ApplicationUser();
        appUser.setUsername(user.getUsername());
        appUser.setPassword(user.getPassword());
        appUser.setRoles("ROLE_USER");
        appUser.setCredentialsNonExpired(true);
        appUser.setAccountNonExpired(true);
        appUser.setEnabled(true);
        appUser.setAccountNonLocked(true);

        given(passwordEncoder.encode(anyString())).willReturn(user.getPassword());

        applicationUserDetailsServiceTest.createUser(user);

        ArgumentCaptor<ApplicationUser> userArgumentCaptor =
                ArgumentCaptor.forClass(ApplicationUser.class);

        verify(applicationUserRepository)
                .save(userArgumentCaptor.capture());

        ApplicationUser capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).usingRecursiveComparison()
                .isEqualTo(appUser);
    }

    @Test
    void createUserThrowsException() {
        UserModel user = new UserModel();
        user.setUsername("test");
        user.setPassword("test-password");

        given(applicationUserRepository.existsById(anyString())).willReturn(true);


        assertThatThrownBy(() -> applicationUserDetailsServiceTest.createUser(user))
                .isInstanceOf(ConflictException.class)
                .hasMessageContaining(String.format("Username %s already Exists",user.getUsername()));
    }
}