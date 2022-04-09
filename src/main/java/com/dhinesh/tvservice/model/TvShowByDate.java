package com.dhinesh.tvservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TvShowByDate {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("season")
    private String season;

    @JsonProperty("number")
    private String episode;

    @JsonProperty("_embedded")
    private TvShowWrapper embedded;


}
