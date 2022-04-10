package com.dhinesh.tvservice.repository;

import com.dhinesh.tvservice.entity.ApplicationUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ApplicationUserRepositoryTest {

    @Autowired
    private ApplicationUserRepository undertestApplicationRepo;

    @AfterEach
    void tearDown() {
        undertestApplicationRepo.deleteAll();
    }

    @Test
    void checkIfFindByUsernameExists() {

        ApplicationUser user = new ApplicationUser();
        user.setUsername("test");
        user.setPassword("test");
        user.setEnabled(true);
        user.setRoles("ROLE_USER");
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);

        undertestApplicationRepo.save(user);

        Optional<ApplicationUser> userExists = undertestApplicationRepo.findByUsername("test");
        assertThat(userExists.isPresent()).isTrue();
    }

    @Test
    void checkIfFindByUsernameDoesNotExists() {

        Optional<ApplicationUser> userExists = undertestApplicationRepo.findByUsername("test");
        assertThat(userExists.isPresent()).isFalse();
    }
}