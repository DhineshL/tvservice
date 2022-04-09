package com.dhinesh.tvservice.consumer;

import com.dhinesh.tvservice.model.TvShow;
import com.dhinesh.tvservice.model.TvShowByDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class TvMazeConsumer {

    private final RestTemplate restTemplate;


    public TvMazeConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TvShow> fetchTvShowPages(int page){

        String url = "http://api.tvmaze.com/shows?page="+page;

        log.info("Fetching Pages");
        ResponseEntity<TvShow[]> response = restTemplate.getForEntity(url , TvShow[].class);
        TvShow[] tvShows = response.getBody();

        return Arrays.asList(tvShows);
    }

    public List<TvShowByDate> fetchTvShowsByDate(String date){

        String url = String.format("https://api.tvmaze.com/schedule/web?date=%s&country=",date);

        log.info("Fetching tvShows by date");
        ResponseEntity<TvShowByDate[]> response = restTemplate.getForEntity(url, TvShowByDate[].class);
        TvShowByDate[] tvShow = response.getBody();

        return Arrays.asList(tvShow);
    }
}
