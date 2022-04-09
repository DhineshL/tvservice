package com.dhinesh.tvservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TvShowModel {

    private Integer id;

    private String name;

    private String language;

    private List<String> genres;

    private Integer rating;

    private Integer weight;

    private String imageUrl;

    private String summary;

    private Long likes;
}
