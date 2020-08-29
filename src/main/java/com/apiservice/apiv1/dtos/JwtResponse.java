package com.apiservice.apiv1.dtos;

public class JwtResponse {
    private final String jwttoken;

    public JwtResponse(String jwttoken) {
        this.jwttoken = "Bearer " + jwttoken;
    }

    public String getToken() {
        return this.jwttoken;
    }

}
