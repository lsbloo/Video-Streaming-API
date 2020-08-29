package com.apiservice.apiv1.service;


import com.apiservice.apiv1.models.Relatorio;
import com.apiservice.apiv1.models.Unidade;
import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.repository.*;
import com.apiservice.apiv1.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImportService {

    private UnidadeRepository unidadeRepository;
    private RelatorioRepository relatorioRepository;
    private UserRepository userRepository;

    @Autowired
    public UserImportService(UnidadeRepository unidadeRepository, RelatorioRepository relatorioRepository, UserRepository userRepository){
        this.unidadeRepository=unidadeRepository;
        this.relatorioRepository=relatorioRepository;
        this.userRepository=userRepository;
    }



    public void importDataSet(List<Unidade> list_dataset, String user_autenticate){
        User u = this.userRepository.getUserByUsername(user_autenticate);
        if(u.getRelatorio() != null){
            this.unidadeRepository.deleteDataRelated(u.getRelatorio().getId(),Constants.TYPE_CSV);
            u.getRelatorio().setList_unidade(list_dataset);
            for (Unidade unity : list_dataset) {
                unity.setRelatorio(u.getRelatorio());
                unity.setType(Constants.TYPE_CSV);
                this.unidadeRepository.save(unity);
            }
        }
        else{
            Relatorio relatorio = new Relatorio();
            Relatorio relatorio1 = this.relatorioRepository.save(relatorio);
            relatorio1.setList_unidade(list_dataset);
            for(Unidade unity : list_dataset){
                unity.setRelatorio(relatorio1);
                unity.setType(Constants.TYPE_CSV);
                this.unidadeRepository.save(unity);
            }
            this.userRepository.updateRelatorioId(relatorio1.getId(),u.getId());
        }

    }
}
