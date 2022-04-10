package com.dhinesh.tvservice.repository;

import com.dhinesh.tvservice.entity.TvUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TvUserRepositoryTest {

    @Autowired
    private TvUserRepository tvUserRepository;


    @AfterEach
    void tearDown() {
        tvUserRepository.deleteAll();
    }

    @Test
    void findByUsername() {
        TvUser user = new TvUser();
        user.setUsername("test");
        user.setLastShowId(12);

        tvUserRepository.save(user);

        Optional<TvUser> tvUser = tvUserRepository.findByUsername("test");
        assertThat(tvUser.isPresent()).isTrue();
    }

    @Test
    void findSavedTvShowsByUsername() {

        Optional<TvUser> tvUser = tvUserRepository.findByUsername("test");
        assertThat(tvUser.isPresent()).isFalse();
    }

}