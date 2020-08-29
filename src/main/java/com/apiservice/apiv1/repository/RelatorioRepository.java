package com.apiservice.apiv1.repository;

import com.apiservice.apiv1.models.Relatorio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RelatorioRepository extends CrudRepository<Relatorio,Integer> {

    @Query(value="select * from relatorio where identifier=:id",nativeQuery = true)
    Relatorio getRelatorioByUser(@Param("id") Integer id);



}
