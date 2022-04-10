package com.dhinesh.tvservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity(name = "tblTvUser")
@Getter
@Setter
public class TvUser {
    @Id
    private String username;

    private Integer lastShowId;

    @ManyToMany
    private Set<TvShowEntity> savedTvShows;

    @ElementCollection
    private List<Integer> featuredTvShows;

}
