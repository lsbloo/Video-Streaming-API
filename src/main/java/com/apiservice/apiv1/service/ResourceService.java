package com.apiservice.apiv1.service;

import com.apiservice.apiv1.dtos.UnidadeResponseDTO;
import com.apiservice.apiv1.models.Relatorio;
import com.apiservice.apiv1.models.Unidade;
import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.repository.RelatorioRepository;
import com.apiservice.apiv1.repository.UnidadeRepository;
import com.apiservice.apiv1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    private UnidadeRepository unidadeRepository;
    private RelatorioRepository relatorioRepository;
    private UserRepository userRepository;

    @Autowired
    public ResourceService(UnidadeRepository unidadeRepository, RelatorioRepository relatorioRepository, UserRepository userRepository){
        this.unidadeRepository=unidadeRepository;
        this.relatorioRepository=relatorioRepository;
        this.userRepository=userRepository;
    }

    public Double[] getMediaPricesByCity(String city, String type_data,String user_auth){
        User user_authe = this.userRepository.getUserByUsername(user_auth);
        Relatorio relatorio = this.relatorioRepository.getRelatorioByUser(user_authe.getRelatorio().getId());
        List<Unidade> unidadeList = this.unidadeRepository.getUnidadeByCity(city.toUpperCase(),type_data,relatorio.getId());
        if(unidadeList.size() == 0) return null;

        List<String> valores_venda_lista = new ArrayList<>();
        List<String> valores_compra_lista = new ArrayList<>();
        for(Unidade u : unidadeList){
            if(u.getValor_venda() != null) {
                valores_venda_lista.add(u.getValor_venda());
            }
            if(u.getValor_compra() != null) {
                valores_compra_lista.add(u.getValor_compra());
            }
        }
        Double[] result = new Double[2];
        result[0] = calculateMediaPrice(valores_venda_lista);
        result[1] = calculateMediaPrice(valores_compra_lista);

        return result;

    }
    public List<UnidadeResponseDTO> getConjuntByData(String data,String type_data ,String user_auth){
        User user_authe = this.userRepository.getUserByUsername(user_auth);
        Relatorio relatorio = this.relatorioRepository.getRelatorioByUser(user_authe.getRelatorio().getId());
        List<Unidade> unidadeList = this.unidadeRepository.getUnidadeByData(data,type_data,relatorio.getId());
        List<UnidadeResponseDTO> dtoList = new ArrayList<>();

        for(Unidade u : unidadeList){
            dtoList.add(new UnidadeResponseDTO(u.getId(),u.getType(),u.getRegiao(),u.getEstado(),
                    u.getMunicipio(),u.getRevenda(),u.getCodigo_instalacao(),
                    u.getProduto(),u.getData_coleta(),u.getValor_compra(),
                    u.getValor_venda(),u.getUnidade_medida(),u.getBandeira()));
        }

        return dtoList;
    }




    public List<UnidadeResponseDTO> getConjuntByDistributor(String distribuidora, String type_data,String user_auth){
        User user_authe = this.userRepository.getUserByUsername(user_auth);
        Relatorio relatorio = this.relatorioRepository.getRelatorioByUser(user_authe.getRelatorio().getId());
        List<Unidade> unidadeList = this.unidadeRepository.getUnidadeByDistro(distribuidora.toUpperCase(),type_data,relatorio.getId());
        List<UnidadeResponseDTO> dtoList = new ArrayList<>();

        for(Unidade u : unidadeList){
            dtoList.add(new UnidadeResponseDTO(u.getId(),u.getType(),u.getRegiao(),u.getEstado(),
                    u.getMunicipio(),u.getRevenda(),u.getCodigo_instalacao(),
                    u.getProduto(),u.getData_coleta(),u.getValor_compra(),
                    u.getValor_venda(),u.getUnidade_medida(),u.getBandeira()));
        }

        return dtoList;
    }
    public List<UnidadeResponseDTO> getConjuntUnidadesByInitial(String sigla, String type_data,String user_auth){
        User user_authe = this.userRepository.getUserByUsername(user_auth);
        Relatorio relatorio = this.relatorioRepository.getRelatorioByUser(user_authe.getRelatorio().getId());
        List<Unidade> unidadeList = this.unidadeRepository.getUnidadeBySigla(sigla.toUpperCase(),type_data,relatorio.getId());
        List<UnidadeResponseDTO> dtoList = new ArrayList<>();
        for(Unidade u : unidadeList){
            dtoList.add(new UnidadeResponseDTO(u.getId(),u.getType(),u.getRegiao(),u.getEstado(),
                    u.getMunicipio(),u.getRevenda(),u.getCodigo_instalacao(),
                    u.getProduto(),u.getData_coleta(),u.getValor_compra(),
                    u.getValor_venda(),u.getUnidade_medida(),u.getBandeira()));
        }

        return dtoList;

    }


    public Double getMediaPriceByCity(String name_city,String type_data ,String user_auth){
        User user_authe = this.userRepository.getUserByUsername(user_auth);
        Relatorio relatorio = this.relatorioRepository.getRelatorioByUser(user_authe.getRelatorio().getId());

        List<String> stringList = new ArrayList<>();
        List<Unidade> unidadeList = this.unidadeRepository.getUnidades(relatorio.getId(),type_data,name_city.toUpperCase());
        for(Unidade unity : unidadeList){
            if(unity.getValor_venda() != null) {
                stringList.add(unity.getValor_venda());
            }
        }
        return calculateMediaPrice(stringList);
    }


    public Double calculateMediaPrice(List<String > stringList){
        if(stringList.size() == 0) return 0.0;

        List<Double> cc = new ArrayList<>();
        for(String x : stringList){
            String[] y = x.split(" ");
            String d = y[0].replaceAll(",",".");
            cc.add(Double.valueOf(d));
        }
        Double somatorio=0.0;
        for(Double item : cc){
            somatorio += item;
        }
        return somatorio / cc.size();
    }

    public Double[] getMediaPricesByFlag(String flag, String typeData , String user_auth){
        User user_authe = this.userRepository.getUserByUsername(user_auth);
        Relatorio relatorio = this.relatorioRepository.getRelatorioByUser(user_authe.getRelatorio().getId());
        List<Unidade> unidadeList = this.unidadeRepository.getUnidadeByFlag(flag.toUpperCase(),typeData,relatorio.getId());
        if(unidadeList.size() == 0) return null;

        List<String> valores_venda_lista = new ArrayList<>();
        List<String> valores_compra_lista = new ArrayList<>();
        for(Unidade u : unidadeList){
            if(u.getValor_venda() != null) {
                valores_venda_lista.add(u.getValor_venda());
            }
            if(u.getValor_compra() != null) {
                valores_compra_lista.add(u.getValor_compra());
            }
        }
        Double[] result = new Double[2];
        result[0] = calculateMediaPrice(valores_venda_lista);
        result[1] = calculateMediaPrice(valores_compra_lista);

        return result;

    }
}
