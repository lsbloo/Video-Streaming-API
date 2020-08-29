package com.apiservice.apiv1.valitador.validators;

import com.apiservice.apiv1.dtos.HistoryCreate;
import com.apiservice.apiv1.dtos.UnidadeResponseDTO;
import com.apiservice.apiv1.models.Unidade;
import com.apiservice.apiv1.repository.UnidadeRepository;
import com.apiservice.apiv1.valitador.core.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HistoryValidator {


    @Autowired
    private UnidadeRepository unidadeRepository;

    public Validator<HistoryCreate> checkExistence(){
        return(result,unidade) -> {
            Unidade unidade1 = this.unidadeRepository.checkExistence(unidade.getBandeira(),unidade.getCodigo_instalacao(),unidade.getData_coleta(),unidade.getEstado(),unidade.getMunicipio(),unidade.getProduto(),unidade.getRegiao(),unidade.getRevenda(),unidade.getUnidade_medida(),unidade.getValor_compra(),unidade.getValor_venda());
            if(unidade1 != null){
                result.error("error");
            }else{
                result.ok();
            }

        };
    }

    public Validator<UnidadeResponseDTO> checkExistenceUpdate(){
        return(result, unidade) -> {
            Unidade unidade1 = this.unidadeRepository.checkExistence(unidade.getBandeira(),unidade.getCodigo_instalacao(),unidade.getData_coleta(),unidade.getEstado(),unidade.getMunicipio(),unidade.getProduto(),unidade.getRegiao(),unidade.getRevenda(),unidade.getUnidade_medida(),unidade.getValor_compra(),unidade.getValor_venda());
            if(unidade1 != null){
                result.ok();
            }else{
                result.error("error");
            }
        };

    }

}
