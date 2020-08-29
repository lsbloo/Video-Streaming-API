package com.apiservice.apiv1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponsePriceCity {

    private String message;
    private String description;
    private String media_price_compra;
    private String media_price_venda;

}
