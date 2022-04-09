package com.dhinesh.tvservice.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Data
public class UserModel {

    @NotEmpty
    private String username;

    @NotEmpty
    @Size(min = 2, max = 10)
    private String password;

}
