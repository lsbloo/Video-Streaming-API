package com.apiservice.apiv1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

// simple class
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String name;
    private String lastName;
    private String username;
    private String password;
    private String email;

    public UserDTO(String name, String lastName, String username, String password){
        this.name=name;
        this.lastName=lastName;
        this.username=username;
        this.password=password;
    }


}
