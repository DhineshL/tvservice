package com.dhinesh.tvservice.controller;

import com.dhinesh.tvservice.consumer.TvMazeConsumer;
import com.dhinesh.tvservice.model.TvShow;
import com.dhinesh.tvservice.model.TvShowModel;
import com.dhinesh.tvservice.model.UserModel;
import com.dhinesh.tvservice.service.ApplicationService;
import com.dhinesh.tvservice.service.TvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("user")
public class UserController {

    private ApplicationService applicationService;

    @Autowired
    public UserController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }


    @GetMapping(value = "suggest",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<TvShowModel> suggestShow(Principal principal){

        TvShowModel tvShow = applicationService.suggestTvShow(principal);

        return new ResponseEntity<>(tvShow,HttpStatus.OK);
    }

    @GetMapping(value="streamingnow",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<TvShowModel>> getTvShowsToday(){
        List<TvShowModel> tvShows = applicationService.getTvShowsToday();

        return new ResponseEntity<>(tvShows,HttpStatus.OK);
    }

    @PostMapping(value="like",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> likedTvShow(@RequestBody TvShowModel tvShowModel , Principal principal){
        applicationService.likeTvShow(tvShowModel,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value="top",produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<TvShowModel>> getTop10TvShows(){
        List<TvShowModel> tvShows = applicationService.getTop10TvShow();

        return new ResponseEntity<>(tvShows,HttpStatus.OK);
    }
}
