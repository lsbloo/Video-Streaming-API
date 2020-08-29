package com.apiservice.apiv1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserGGDTO {

    private String name;
    private String lastName;
    private String username;
    private String email;
    private Integer id;

}
