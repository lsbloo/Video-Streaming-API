package com.apiservice.apiv1.models;

import lombok.*;


@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CsvReader{
    @Setter @Getter
    private String regiao;
    @Setter @Getter
    private String estado;
    @Setter @Getter
    private String municipio;
    @Setter @Getter
    private String revenda;
    @Setter @Getter
    private String codigo_instalacao;
    @Setter @Getter
    private String produto;
    @Setter @Getter
    private String data_coleta;
    @Setter @Getter
    private String valor_compra;
    @Setter @Getter
    private String valor_venda;

    @Setter @Getter
    private String unidade_medida;

    @Setter @Getter
    private String bandeira;
}
