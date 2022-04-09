package com.dhinesh.tvservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class Links {

    @JsonProperty("self")
    private Self self;
    @JsonProperty("previousepisode")
    private Previousepisode previousepisode;

}
