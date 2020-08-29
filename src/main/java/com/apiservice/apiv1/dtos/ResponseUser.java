package com.apiservice.apiv1.dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUser {
    private String result;

    private String description;

    public ResponseUser(String result, String description){
        this.description= description;
        this.result=result;

    }
}
