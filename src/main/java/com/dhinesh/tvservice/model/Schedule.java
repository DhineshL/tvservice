package com.dhinesh.tvservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class Schedule {

    @JsonProperty("time")
    private String time;
    @JsonProperty("days")
    private List<String> days = null;

}
