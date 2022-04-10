package com.dhinesh.tvservice.entity;


import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity(name = "tblTvUser")
@Data
public class TvUser {
    @Id
    private String username;

    private int lastShowId;

    @ManyToMany
    private Set<TvShowEntity> savedTvShows;

    @ElementCollection
    private List<Integer> featuredTvShows;

}
