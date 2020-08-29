package com.apiservice.apiv1.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

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

    private String type;



    public Unidade(String regiao, String estado, String municipio, String revenda , String codigo_instalacao, String produto, String data_coleta,String valor_compra, String valor_venda, String unidade_medida, String bandeira){
        setRegiao(regiao);
        setEstado(estado);
        setMunicipio(municipio);
        setRevenda(revenda);
        setCodigo_instalacao(codigo_instalacao);
        setProduto(produto);
        setData_coleta(data_coleta);
        setValor_compra(valor_compra);
        setValor_venda(valor_venda);
        setUnidade_medida(unidade_medida);
        setBandeira(bandeira);
    }

    @ManyToOne
    private Relatorio relatorio;

}
