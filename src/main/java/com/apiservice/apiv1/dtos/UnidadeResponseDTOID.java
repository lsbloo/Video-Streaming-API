package com.apiservice.apiv1.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnidadeResponseDTOID {

    private String regiao;

    private String estado;

    private String municipio;

    private String revenda;

    private String codigo_instalacao;

    private String produto;

    private String data_coleta;

    private String valor_compra;

    private String valor_venda;

    private String unidade_medida;

    private String bandeira;

    private Integer id;
}
