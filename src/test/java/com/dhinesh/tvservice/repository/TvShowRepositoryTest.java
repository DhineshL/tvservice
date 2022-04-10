package com.dhinesh.tvservice.repository;

import com.dhinesh.tvservice.entity.TvShowEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class TvShowRepositoryTest {

    @Autowired
    private TvShowRepository underTvShowRepository;

    @AfterEach
    void tearDown() {
        underTvShowRepository.deleteAll();
    }

    @Test
    void checkIfFindFirst10ByOrderByLikesDescExists() {
        List<TvShowEntity> testTvShow = new ArrayList<>();

        for(int i=0;i<12;i++){
            TvShowEntity testTvShowEntity = new TvShowEntity();
            testTvShowEntity.setId(i);
            testTvShowEntity.setLikes((long) (12-i));

            underTvShowRepository.save(testTvShowEntity);

            if(i<10)  testTvShow.add(testTvShowEntity);
        }

        Assertions.assertThat(testTvShow).isEqualTo(underTvShowRepository.findFirst10ByOrderByLikesDesc());
    }

}