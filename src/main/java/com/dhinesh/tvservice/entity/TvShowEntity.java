package com.dhinesh.tvservice.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;

@Entity(name="tblShow")
@Data
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

    @ElementCollection
    private Set<String> users;

    private Long likes;

}
