package com.apiservice.apiv1.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UnidadeResponseDTO {

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

    private String type;


    public UnidadeResponseDTO(Integer id,String type ,String regiao, String estado, String municipio, String revenda, String codigo_instalacao, String produto, String data_coleta, String valor_compra, String valor_venda, String unidade_medida, String bandeira) {
        this.regiao = regiao;
        this.estado = estado;
        this.municipio = municipio;
        this.revenda = revenda;
        this.codigo_instalacao = codigo_instalacao;
        this.produto = produto;
        this.data_coleta = data_coleta;
        this.valor_compra = valor_compra;
        this.valor_venda = valor_venda;
        this.unidade_medida = unidade_medida;
        this.bandeira = bandeira;
        this.id= id;
        this.type=type;
    }
}
