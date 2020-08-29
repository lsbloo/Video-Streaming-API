package com.apiservice.apiv1.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAuth {
    private String username;
    private String password;
}
