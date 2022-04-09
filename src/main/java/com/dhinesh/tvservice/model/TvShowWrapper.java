package com.dhinesh.tvservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TvShowWrapper {
    @JsonProperty("show")
    private TvShow tvShow;
}
