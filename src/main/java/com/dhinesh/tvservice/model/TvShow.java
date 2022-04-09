package com.dhinesh.tvservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class TvShow {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("url")
    private String url;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("language")
    private String language;
    @JsonProperty("genres")
    private List<String> genres;
    @JsonProperty("status")
    private String status;
    @JsonProperty("runtime")
    private Integer runtime;
    @JsonProperty("averageRuntime")
    private Integer averageRuntime;
    @JsonProperty("premiered")
    private String premiered;
    @JsonProperty("ended")
    private String ended;
    @JsonProperty("officialSite")
    private String officialSite;
    @JsonProperty("schedule")
    private Schedule schedule;
    @JsonProperty("rating")
    private Rating rating;
    @JsonProperty("weight")
    private Integer weight;
    @JsonProperty("network")
    private Network network;
    @JsonProperty("image")
    private Image image;
    @JsonProperty("summary")
    private String summary;
    @JsonProperty("updated")
    private Integer updated;
    @JsonProperty("_links")
    private Links links;

}
