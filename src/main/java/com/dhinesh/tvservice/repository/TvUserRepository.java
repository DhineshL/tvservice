package com.dhinesh.tvservice.repository;

import com.dhinesh.tvservice.entity.TvShowEntity;
import com.dhinesh.tvservice.entity.TvUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TvUserRepository extends JpaRepository<TvUser,String> {

    Optional<TvUser> findByUsername(String userName);

    TvUser findSavedTvShowsByUsername(String username);
}
