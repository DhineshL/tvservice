package com.dhinesh.tvservice.util;

import com.dhinesh.tvservice.entity.ApplicationUser;
import com.dhinesh.tvservice.entity.TvShowEntity;
import com.dhinesh.tvservice.entity.TvUser;
import com.dhinesh.tvservice.model.TvShow;
import com.dhinesh.tvservice.model.TvShowByDate;
import com.dhinesh.tvservice.model.TvShowModel;
import com.dhinesh.tvservice.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Utility {


   public static ApplicationUser createUserFromModel(UserModel userModel, PasswordEncoder passwordEncoder){

       ApplicationUser applicationUser = new ApplicationUser();
       applicationUser.setUsername(userModel.getUsername());

       // Encoding password
       applicationUser.setPassword(passwordEncoder.encode(userModel.getPassword()));

       applicationUser.setEnabled(true);
       applicationUser.setRoles("ROLE_USER");
       applicationUser.setAccountNonExpired(true);
       applicationUser.setAccountNonLocked(true);
       applicationUser.setCredentialsNonExpired(true);

       return applicationUser;
   }

   public static TvShowModel createTvShowModelFromTvShow(TvShow tvShow){

       TvShowModel tvShowModel = new TvShowModel();

       tvShowModel.setId(tvShow.getId());
       tvShowModel.setName(tvShow.getName());
       tvShowModel.setGenres(tvShow.getGenres());
       tvShowModel.setImageUrl(tvShow.getImage()!=null?tvShow.getImage().getMedium():null);
       tvShowModel.setLanguage(tvShow.getLanguage());
       tvShowModel.setRating(tvShow.getRating()!=null?tvShow.getRating().getAverage():null);
       tvShowModel.setSummary(tvShow.getSummary());
       tvShowModel.setWeight(tvShow.getWeight());

       return tvShowModel;
   }

    public static TvShowModel createTvShowModelFromTvShowByDate(TvShowByDate tvShowByDate){

        TvShow tvShow = tvShowByDate.getEmbedded().getTvShow();

        TvShowModel tvShowModel = new TvShowModel();

        tvShowModel.setId(tvShow.getId());
        tvShowModel.setName(tvShow.getName());
        tvShowModel.setGenres(tvShow.getGenres());
        tvShowModel.setImageUrl(tvShow.getImage()!=null?tvShow.getImage().getMedium():null);
        tvShowModel.setLanguage(tvShow.getLanguage());
        tvShowModel.setRating(tvShow.getRating()!=null?tvShow.getRating().getAverage():null);
        tvShowModel.setSummary(tvShow.getSummary());
        tvShowModel.setWeight(tvShow.getWeight());

        return tvShowModel;
    }


    public static TvUser createTvUserFromApplicationUser(UserModel userModel){

        TvUser tvUser = new TvUser();
        tvUser.setUsername(userModel.getUsername());
        tvUser.setLastShowId(1);

        return tvUser;
    }

    public static TvShowEntity createTvShowEntityFromTvShowModal(TvShowModel tvShowModel){

       TvShowEntity tvShowEntity = new TvShowEntity();
       tvShowEntity.setId(tvShowModel.getId());
       tvShowEntity.setGenres(String.join(", ", tvShowModel.getGenres()));
       tvShowEntity.setImageUrl(tvShowModel.getImageUrl());
       tvShowEntity.setLanguage(tvShowModel.getLanguage());
       tvShowEntity.setName(tvShowModel.getName());
       tvShowEntity.setRating(tvShowModel.getRating());
       tvShowEntity.setSummary(tvShowModel.getSummary());
       tvShowEntity.setWeight(tvShowModel.getWeight());
       tvShowEntity.setLikes(1l);
       tvShowEntity.setUsers(new HashSet<>());

       return tvShowEntity;
    }

    public static TvShowModel createTvShowModalFromTvUserEntity(TvShowEntity tvShowEntity){

        TvShowModel tvShowModel = new TvShowModel();
        tvShowModel.setId(tvShowEntity.getId());
        tvShowModel.setGenres(tvShowEntity.getGenres().isEmpty()? Collections.emptyList() :Arrays.asList(tvShowEntity.getGenres().split(",")));
        tvShowModel.setImageUrl(tvShowEntity.getImageUrl());
        tvShowModel.setLanguage(tvShowEntity.getLanguage());
        tvShowModel.setName(tvShowEntity.getName());
        tvShowModel.setRating(tvShowEntity.getRating());
        tvShowModel.setSummary(tvShowEntity.getSummary());
        tvShowModel.setWeight(tvShowEntity.getWeight());
        tvShowModel.setLikes(tvShowEntity.getLikes());

        return tvShowModel;
    }
}
