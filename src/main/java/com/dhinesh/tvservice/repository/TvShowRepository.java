package com.dhinesh.tvservice.repository;

import com.dhinesh.tvservice.entity.TvShowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TvShowRepository extends JpaRepository<TvShowEntity, Integer> {
    List<TvShowEntity> findFirst10ByOrderByLikesDesc();
}
