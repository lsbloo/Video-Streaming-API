package com.apiservice.apiv1.service;

import com.apiservice.apiv1.context.ContextUtil;
import com.apiservice.apiv1.core.Constants;
import com.apiservice.apiv1.dtos.HistoryCreate;
import com.apiservice.apiv1.dtos.UnidadeResponseDTO;
import com.apiservice.apiv1.dtos.UnidadeResponseDTOID;
import com.apiservice.apiv1.interfaces.HistoryCrudInterface;
import com.apiservice.apiv1.models.Relatorio;
import com.apiservice.apiv1.models.Unidade;
import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.repository.RelatorioRepository;
import com.apiservice.apiv1.repository.UnidadeRepository;
import com.apiservice.apiv1.repository.UserRepository;
import com.apiservice.apiv1.valitador.core.Result;
import com.apiservice.apiv1.valitador.core.ValidatorBuilder;
import com.apiservice.apiv1.valitador.validators.HistoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HistoryService implements HistoryCrudInterface {


    private UserRepository userRepository;
    private UnidadeRepository unidadeRepository;
    private RelatorioRepository relatorioRepository;
    private HistoryValidator historyValidator;

    @Autowired
    public HistoryService(HistoryValidator historyValidator,UserRepository userRepository, UnidadeRepository unidadeRepository, RelatorioRepository relatorioRepository){
        this.userRepository=userRepository;
        this.unidadeRepository=unidadeRepository;
        this.relatorioRepository=relatorioRepository;
        this.historyValidator=historyValidator;
    }


    @Override
    public boolean createHistory(HistoryCreate dto, String user_authe) {
        Result result = new ValidatorBuilder<HistoryCreate>().
                apply(this.historyValidator.checkExistence()).validate(dto);
        if(result.ok()){
            User u = this.userRepository.getUserByUsername(user_authe);
            if(u.getRelatorio() == null) return false;

                Unidade unidade = new Unidade();
                unidade.setType("history");
                unidade.setRelatorio(u.getRelatorio());
                unidade.setBandeira(dto.getBandeira());
                unidade.setUnidade_medida(dto.getUnidade_medida());
                unidade.setValor_venda(dto.getValor_venda());
                unidade.setValor_compra(dto.getValor_compra());
                unidade.setData_coleta(dto.getData_coleta());
                unidade.setProduto(dto.getProduto());
                unidade.setCodigo_instalacao(dto.getCodigo_instalacao());
                unidade.setRevenda(dto.getRevenda());
                unidade.setMunicipio(dto.getMunicipio());
                unidade.setRegiao(dto.getRegiao());
                unidade.setEstado(dto.getEstado());
                this.unidadeRepository.save(unidade);
                return true;

        }
        return false;
    }

    @Override
    public boolean updateHistory(@NotNull HistoryCreate dto, Integer id_history) {
        try{
            Unidade unidade = this.unidadeRepository.getUnidadeById(id_history);
            if(unidade!=null){
                Integer result = this.unidadeRepository.updateHistory(dto.getBandeira(),dto.getCodigo_instalacao(),dto.getData_coleta(),dto.getEstado(),dto.getMunicipio(),dto.getProduto(),dto.getRegiao(),dto.getRevenda(),dto.getUnidade_medida(),dto.getValor_compra(),dto.getValor_venda(), id_history);
                if(result !=0) return true;

            }

        }catch (NullPointerException e){
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteHistory(Integer id_history) {
        try{
            Unidade unidade = this.unidadeRepository.getUnidadeById(id_history);
            if(unidade != null){
                this.unidadeRepository.delete(unidade);
                return true;
            }
        }catch (NullPointerException e){
            return false;
        }
        return false;
    }

    @Override
    public UnidadeResponseDTO getHistory(Integer id_history) {
        try{
            Unidade unidade = this.unidadeRepository.getUnidadeById(id_history);
            if(unidade!=null){
                UnidadeResponseDTO unidadeResponseDTO = new UnidadeResponseDTO();
                unidadeResponseDTO.setBandeira(unidade.getBandeira());
                unidadeResponseDTO.setUnidade_medida(unidade.getUnidade_medida());
                unidadeResponseDTO.setValor_venda(unidade.getValor_venda());
                unidadeResponseDTO.setValor_compra(unidade.getValor_compra());
                unidadeResponseDTO.setData_coleta(unidade.getData_coleta());
                unidadeResponseDTO.setProduto(unidade.getProduto());
                unidadeResponseDTO.setCodigo_instalacao(unidade.getCodigo_instalacao());
                unidadeResponseDTO.setRevenda(unidade.getRevenda());
                unidadeResponseDTO.setRegiao(unidade.getRegiao());
                unidadeResponseDTO.setEstado(unidade.getEstado());
                unidadeResponseDTO.setMunicipio(unidade.getMunicipio());
                return unidadeResponseDTO;
            }
        }catch (NullPointerException e){
            return null;
        }
        return null;
    }

    public List<UnidadeResponseDTOID> getHistorys(){
        User u = this.userRepository.getUserByUsername(ContextUtil.getAuthenticationUsername());
        List<Unidade> unidadeList = this.unidadeRepository.getHistorys(u.getRelatorio().getId(), Constants.TYPE_HISTORY);
        List<UnidadeResponseDTOID> unidadeResponseDTOIDList = new ArrayList<>();
        for(Unidade unidade : unidadeList ){
            UnidadeResponseDTOID unidadeResponseDTOID = new UnidadeResponseDTOID();
            unidadeResponseDTOID.setCodigo_instalacao(unidade.getCodigo_instalacao());

            unidadeResponseDTOID.setId(unidade.getId());
            unidadeResponseDTOID.setBandeira(unidade.getBandeira());
            unidadeResponseDTOID.setData_coleta(unidade.getData_coleta());
            unidadeResponseDTOID.setMunicipio(unidade.getMunicipio());
            unidadeResponseDTOID.setEstado(unidade.getEstado());
            unidadeResponseDTOID.setRegiao(unidade.getRegiao());
            unidadeResponseDTOID.setUnidade_medida(unidade.getUnidade_medida());
            unidadeResponseDTOID.setRevenda(unidade.getRevenda());
            unidadeResponseDTOID.setValor_compra(unidade.getValor_compra());
            unidadeResponseDTOID.setValor_venda(unidade.getValor_venda());
            unidadeResponseDTOID.setProduto(unidade.getProduto());
            unidadeResponseDTOID.setCodigo_instalacao(unidade.getCodigo_instalacao());

            unidadeResponseDTOIDList.add(unidadeResponseDTOID);

        }

        return unidadeResponseDTOIDList;
    }
}
