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
    private final String FETCH_TV_SHOWS_BY_PAGINATION = "http://api.tvmaze.com/shows?page=";
    private final String FETCH_TV_SHOWS_BY_DATE = "https://api.tvmaze.com/schedule/web?date=%s&country=";


    public TvMazeConsumer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches List of TvShows using pagination
     * 1 page <= 250 records
     *
     * @param page
     * @return List<TvShow>
     */

    public List<TvShow> fetchTvShowPages(int page){

        String url = FETCH_TV_SHOWS_BY_PAGINATION+page;

        log.info("Fetching Pages");
        ResponseEntity<TvShow[]> response = restTemplate.getForEntity(url , TvShow[].class);
        TvShow[] tvShows = response.getBody();

        return Arrays.asList(tvShows);
    }

    /**
     * Fetches tvshows that are hiring on a given date
     *
     * @param date
     * @return List<TvShowByDate>
     */
    public List<TvShowByDate> fetchTvShowsByDate(String date){

        String url = String.format(FETCH_TV_SHOWS_BY_DATE,date);

        log.info("Fetching tvShows by date");
        ResponseEntity<TvShowByDate[]> response = restTemplate.getForEntity(url, TvShowByDate[].class);
        TvShowByDate[] tvShow = response.getBody();

        return Arrays.asList(tvShow);
    }
}
