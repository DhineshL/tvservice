package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.consumer.TvMazeConsumer;
import com.dhinesh.tvservice.entity.TvShowEntity;
import com.dhinesh.tvservice.entity.TvUser;
import com.dhinesh.tvservice.exception.NotFoundException;
import com.dhinesh.tvservice.model.TvShow;
import com.dhinesh.tvservice.model.TvShowByDate;
import com.dhinesh.tvservice.model.TvShowModel;
import com.dhinesh.tvservice.repository.TvShowRepository;
import com.dhinesh.tvservice.repository.TvUserRepository;
import com.dhinesh.tvservice.util.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TvServiceTest {

    @Mock private TvShowRepository tvShowRepository;
    @Mock private TvUserRepository tvUserRepository;
    @Mock private TvMazeConsumer tvMazeConsumer;

    private TvService tvServiceUnderTest;

    @BeforeEach
    void setUp() {
        tvServiceUnderTest = new TvService(tvShowRepository,tvUserRepository,tvMazeConsumer);
    }

    @Test
    void canGetTvUserByUserName() {

        String username = "test";
        TvUser tvUser =  new TvUser();
        tvUser.setUsername(username);

        given(tvUserRepository.findByUsername(anyString())).willReturn(Optional.of(tvUser));

        assertThat(tvServiceUnderTest.getTvUserByUserName(username)).isEqualTo(tvUser);
    }

    @Test
    void throwExceptionWhenGetTvUserByUserNameNotExists() {

        String username = "test";

        assertThatThrownBy(() -> tvServiceUnderTest.getTvUserByUserName(username))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format("User name %s does not exist",username));

    }

    @Test
    void saveTvUser() {

        TvUser tvUser = new TvUser( );

        tvServiceUnderTest.saveTvUser(tvUser);

        ArgumentCaptor<TvUser> studentArgumentCaptor =
                ArgumentCaptor.forClass(TvUser.class);

        verify(tvUserRepository)
                .save(studentArgumentCaptor.capture());

        TvUser capturedTvUser = studentArgumentCaptor.getValue();

        assertThat(capturedTvUser).isEqualTo(tvUser);

    }

    @Test
    void saveTvShow() {

        TvShowEntity tvShowEntity = new TvShowEntity( );
        tvShowEntity.setId(1);

        tvServiceUnderTest.saveTvShow(tvShowEntity);

        ArgumentCaptor<TvShowEntity> studentArgumentCaptor =
                ArgumentCaptor.forClass(TvShowEntity.class);

        verify(tvShowRepository)
                .save(studentArgumentCaptor.capture());

        TvShowEntity capturedTvShowEntity = studentArgumentCaptor.getValue();

        assertThat(capturedTvShowEntity).isEqualTo(tvShowEntity);
    }

    @Test
    void fetchTvShowPages() {

        List<TvShow> tvShows = new ArrayList<>();
        Map<Integer,TvShowModel> tvMap = new HashMap<>();

        TvShow tvShow = new TvShow( );
        tvShow.setId(0);
        tvShow.setName("test");
        tvShows.add(tvShow);
        tvMap.put(0,Utility.createTvShowModelFromTvShow(tvShows.get(0)));

        given(tvMazeConsumer.fetchTvShowPages(anyInt())).willReturn(tvShows);

        assertThat(tvServiceUnderTest.fetchTvShowPages(0))
                .usingRecursiveComparison()
                .isEqualTo(tvMap);


    }

    @Test
    void fetchTvShowsByDate() {

        List<TvShowByDate> tvShowByDates = new ArrayList<>();


        given(tvMazeConsumer.fetchTvShowsByDate(anyString())).willReturn(tvShowByDates);

        assertThat(tvMazeConsumer.fetchTvShowsByDate(anyString())).isEqualTo(tvShowByDates);
    }

    @Test
    void getTvShowEntityById() {

        TvShowEntity tvShowEntity = new TvShowEntity( );
        tvShowEntity.setId(1);

        given(tvShowRepository.findById(anyInt())).willReturn(Optional.of(tvShowEntity));

        assertThat(tvServiceUnderTest.getTvShowEntityById(1)).isEqualTo(Optional.of(tvShowEntity));

    }

    @Test
    void getTop10TvShow() {
        Collection<TvShowEntity> tvShowEntity = new ArrayList<>();


        given(tvShowRepository.findFirst10ByOrderByLikesDesc()).willReturn((List<TvShowEntity>) tvShowEntity);

        assertThat(tvServiceUnderTest.getTop10TvShow()).isEqualTo(tvShowEntity);
    }

    @Test
    void getSavedTvShows() {

        String username = "test";

        TvUser tvUser = new TvUser();
        Set<TvShowEntity> tvShowEntity = new HashSet<>();

        tvUser.setUsername(username);
        tvUser.setSavedTvShows(tvShowEntity);


        given(tvUserRepository.findSavedTvShowsByUsername(anyString())).willReturn(tvUser);

        assertThat(tvServiceUnderTest.getSavedTvShows(username)).isEqualTo(tvShowEntity);
    }
}