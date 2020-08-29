package com.apiservice.apiv1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourcePriceCityDTO {

    private String message;
    private String description;
    private String city;
    private String price;

}
