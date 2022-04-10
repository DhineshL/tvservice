package com.dhinesh.tvservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Set;

@Entity(name="tblShow")
@Getter
@Setter
public class TvShowEntity {
    @Id
    private Integer id;

    private String name;

    private String language;

    private String genres;

    private Integer rating;

    private Integer weight;

    private String imageUrl;

    @Column(length=10000)
    private String summary;

    private Set<String> users;

    private Long likes;


}
