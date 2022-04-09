package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.entity.TvShowEntity;
import com.dhinesh.tvservice.entity.TvUser;
import com.dhinesh.tvservice.exception.AlreadyLikedTvShow;
import com.dhinesh.tvservice.model.TvShowModel;
import com.dhinesh.tvservice.model.UserModel;
import com.dhinesh.tvservice.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private TvService tvService;

    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    public ApplicationService(TvService tvService, ApplicationUserDetailsService applicationUserDetailsService) {
        this.tvService = tvService;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    public void createUser(UserModel user) {

        // Creates user and login
        applicationUserDetailsService.createUser(user);

        // Create Tv User with showId 1
        TvUser tvUser = Utility.createTvUserFromApplicationUser(user);

        tvService.saveTvUser(tvUser);

    }

    public TvShowModel suggestTvShow(Principal user) {

        String userName = user.getName();



        // Get user from userTable
        TvUser tvUser = tvService.getTvUserByUserName(userName);

        int id = tvUser.getLastShowId();

        int page = id / 250;

        // fetch the show using user.showId use page = showId/256 and iterate for showId
        Map<Integer, TvShowModel> tvShows = tvService.fetchTvShowPages(page);

        TvShowModel tvShowModel = tvShows.get(id);

        tvUser.setLastShowId(++id);
        tvService.saveTvUser(tvUser);

        return tvShowModel;
    }

    public List<TvShowModel> getTvShowsToday() {

        // fetches tv shows hiring today by passing today's date
        return tvService.fetchTvShowsByDate(LocalDate.now().toString());
    }

    public void likeTvShow(TvShowModel tvShowModel, Principal principal) {

        String username = principal.getName();
        Integer tvShowId = tvShowModel.getId();

        TvShowEntity tvShow;
        Optional<TvShowEntity> tvShowEntity = tvService.getTvShowEntityById(tvShowId);

        // Checks if show is empty
        if (tvShowEntity.isEmpty()) {
            tvShow = Utility.createTvShowEntityFromTvUserModal(tvShowModel);
            tvShow.setUsers(username);
        } else {

            tvShow = tvShowEntity.get();
            Set<String> users = new HashSet<>(Arrays.asList(tvShow.getUsers().split(",")));

            if (users.contains(username))
                throw new AlreadyLikedTvShow(String.format("Already Liked show %s", tvShowId.toString()));

            // Increments like and users to liked usernames to prevent further likes
            users.add(username);
            tvShow.setUsers(String.join(",", users));
            tvShow.setLikes(tvShow.getLikes() + 1);
        }

        tvService.saveTvShow(tvShow);

    }


    public List<TvShowModel> getTop10TvShow() {

        return tvService.getTop10TvShow().
                stream().map(Utility::createTvShowModalFromTvUserEntity).
                collect(Collectors.toList());
    }

}
