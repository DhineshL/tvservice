package com.dhinesh.tvservice.service;

import com.dhinesh.tvservice.entity.TvShowEntity;
import com.dhinesh.tvservice.entity.TvUser;
import com.dhinesh.tvservice.exception.ConflictException;
import com.dhinesh.tvservice.exception.NotFoundException;
import com.dhinesh.tvservice.model.TvShowByDate;
import com.dhinesh.tvservice.model.TvShowModel;
import com.dhinesh.tvservice.model.UserModel;
import com.dhinesh.tvservice.util.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplicationService {

    private TvService tvService;

    private ApplicationUserDetailsService applicationUserDetailsService;

    @Autowired
    public ApplicationService(TvService tvService, ApplicationUserDetailsService applicationUserDetailsService) {
        this.tvService = tvService;
        this.applicationUserDetailsService = applicationUserDetailsService;
    }

    /**
     * Creates a user if a user doesn't exist or else throws an exception
     *
     * @param user
     */
    public void createUser(UserModel user) {

        // Creates user and login
        applicationUserDetailsService.createUser(user);

        // Create Tv User with showId 1
        TvUser tvUser = Utility.createTvUserFromApplicationUser(user);

        tvService.saveTvUser(tvUser);

    }

    /**
     * Suggests random tvshow to user by incrementing showid
     *
     * @param user
     * @return
     */

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

    /**
     * fetches tvhows hiring today
     *
     * @return
     */

    public List<TvShowModel> getTvShowsToday() {

        // fetches tv shows hiring today by passing today's date
        List<TvShowByDate> tvShows =  tvService.fetchTvShowsByDate(LocalDate.now().toString());

        return tvShows.stream()
                .map(Utility::createTvShowModelFromTvShowByDate)
                .collect(Collectors.toList());
    }

    public void likeTvShow(TvShowModel tvShowModel, Principal principal) {

        String username = principal.getName();
        Integer tvShowId = tvShowModel.getId();

        TvShowEntity tvShow;
        Optional<TvShowEntity> tvShowEntity = tvService.getTvShowEntityById(tvShowId);

        // Checks if show is empty
        if (tvShowEntity.isEmpty()) {
            tvShow = Utility.createTvShowEntityFromTvShowModal(tvShowModel);
            tvShow.getUsers().add(username);
        } else {

            tvShow = tvShowEntity.get();
            Set<String> users = tvShow.getUsers();

            if (users.contains(username))
                throw new ConflictException(String.format("Already Liked show %s", tvShowId.toString()));

            // Increments like and users to liked usernames to prevent further likes
            tvShow.getUsers().add(username);
            tvShow.setLikes(tvShow.getLikes() + 1);
        }

        tvService.saveTvShow(tvShow);

    }

    /**
     * fetches top 10 liked tv shows
     *
     * @return
     */

    public List<TvShowModel> getTop10TvShow() {

        return tvService.getTop10TvShow().
                stream().map(Utility::createTvShowModalFromTvUserEntity).
                collect(Collectors.toList());
    }

    public void saveTvShowForUser(TvShowModel tvShowModel, Principal principal){
        String username = principal.getName();
        TvUser tvUser = tvService.getTvUserByUserName(username);
        TvShowEntity tvShowEntity = Utility.createTvShowEntityFromTvShowModal(tvShowModel);


        if(! tvUser.getSavedTvShows().contains(tvShowEntity)){

            tvUser.getSavedTvShows().add(tvShowEntity);
            tvService.saveTvShow(tvShowEntity);
            tvService.saveTvUser(tvUser);
        }

    }

    public void featureTvShowForUser(TvShowModel tvShowModel, Principal principal){
        String username = principal.getName();
        TvUser tvUser = tvService.getTvUserByUserName(username);

        TvShowEntity tvShowEntity = Utility.createTvShowEntityFromTvShowModal(tvShowModel);
        if(!tvUser.getSavedTvShows().contains(tvShowEntity)){
            throw new ConflictException("Cannot add Unsaved TvShow");
        }

        tvUser.getFeaturedTvShows().add(tvShowEntity.getId());

        tvService.saveTvUser(tvUser);
    }

    public List<TvShowModel> getSavedTvShows(Principal principal){
        String username = principal.getName();

        return tvService.getSavedTvShows(username).
                stream().map(Utility::createTvShowModalFromTvUserEntity).
                collect(Collectors.toList());
    }

    public void deleteTvShowForUser(int id, Principal principal){
        String username = principal.getName();
        TvUser tvUser = tvService.getTvUserByUserName(username);
        Set<TvShowEntity> savedShows = tvUser.getSavedTvShows();
        log.info("herer");
        log.info(savedShows.toString());

        Optional<TvShowEntity> tvShow = savedShows.stream().filter((show)->show.getId()==id).findFirst();

        if(tvShow.isEmpty()) throw new NotFoundException((String.format("resource %i not found",id)));

        tvUser.getSavedTvShows().remove(tvShow.get());
        tvService.saveTvUser(tvUser);
    }

}
