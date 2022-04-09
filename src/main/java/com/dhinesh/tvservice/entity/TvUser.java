package com.dhinesh.tvservice.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "tblTvUser")
@Getter
@Setter
public class TvUser {
    @Id
    private String username;

    private Integer lastShowId;

}
