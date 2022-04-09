package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.consumer.TvMazeConsumer;
import com.dhinesh.tvservice.entity.TvShowEntity;
import com.dhinesh.tvservice.entity.TvUser;
import com.dhinesh.tvservice.model.TvShow;
import com.dhinesh.tvservice.model.TvShowByDate;
import com.dhinesh.tvservice.model.TvShowModel;
import com.dhinesh.tvservice.repository.TvShowRepository;
import com.dhinesh.tvservice.repository.TvUserRepository;
import com.dhinesh.tvservice.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TvService {

    private TvShowRepository tvShowRepository;
    private TvUserRepository tvUserRepository;
    private TvMazeConsumer tvMazeConsumer;

    @Autowired
    public TvService(TvShowRepository tvShowRepository, TvUserRepository tvUserRepository, TvMazeConsumer tvMazeConsumer) {
        this.tvShowRepository = tvShowRepository;
        this.tvUserRepository = tvUserRepository;
        this.tvMazeConsumer = tvMazeConsumer;
    }

    public TvUser getTvUserByUserName(String userName){

        return tvUserRepository.findByUsername(userName)
                .orElseThrow(()-> new RuntimeException(String.format("User name %s does not exist",userName)));
    }

    public void saveTvUser(TvUser tvUser){

        tvUserRepository.save(tvUser);
    }

    public void saveTvShow(TvShowEntity tvShowEntity){

        tvShowRepository.save(tvShowEntity);
    }

    @Cacheable(value="tvShows",key = "#page")
    public Map<Integer,TvShowModel> fetchTvShowPages(int page){

        List<TvShow> tvShows = tvMazeConsumer.fetchTvShowPages(page);

        List<TvShowModel> tvShowModels = tvShows.stream().map(Utility::createTvShowModelFromTvShow).collect(Collectors.toList());

        Map<Integer,TvShowModel> tvShowMap = tvShowModels.stream()
                .collect(Collectors.toMap(TvShowModel::getId, Function.identity(),(prev, next) -> next, HashMap::new));

        return tvShowMap;
    }

    @Cacheable(value="tvShowsByDate",key = "#date")
    public List<TvShowModel> fetchTvShowsByDate(String date){

        List<TvShowByDate> tvShows = tvMazeConsumer.fetchTvShowsByDate(date);
        List<TvShowModel> tvShowModels = tvShows.stream().map(Utility::createTvShowModelFromTvShowByDate).collect(Collectors.toList());

        return tvShowModels;
    }

    public Optional<TvShowEntity> getTvShowEntityById(Integer id){

        return tvShowRepository.findById(id);
    }

    public List<TvShowEntity> getTop10TvShow(){
        return tvShowRepository.findFirst10ByOrderByLikesDesc();
    }
}
