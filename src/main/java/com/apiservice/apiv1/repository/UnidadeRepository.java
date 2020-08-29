package com.apiservice.apiv1.repository;

import com.apiservice.apiv1.models.Relatorio;
import com.apiservice.apiv1.models.Unidade;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UnidadeRepository extends CrudRepository<Unidade, Integer> {

    @Query(value="select * from unidade where id=:id", nativeQuery=true)
    Unidade getUnidadeById(@Param("id") Integer id);


    @Query(value="select * from unidade where bandeira=:bandeira and codigo_instalacao=:codigo_instalacao and data_coleta=:data_coleta and estado=:estado and municipio=:municipio and produto=:produto and regiao=:regiao and revenda=:revenda and unidade_medida=:unidade_medida and valor_compra=:valor_compra and valor_venda=:valor_venda",nativeQuery = true)
    Unidade checkExistence(@Param("bandeira") String bandeira, @Param("codigo_instalacao") String codigo_instalacao,@Param("data_coleta") String data_coleta,@Param("estado")String estado,@Param("municipio")String municipio,@Param("produto")String produto, @Param("regiao")String regiao,@Param("revenda")String revenda,@Param("unidade_medida") String unidade_medida, @Param("valor_compra") String valor_compra, @Param("valor_venda") String valor_venda );


    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.municipio=:municipio and u.type=:type")
    List<Unidade> getUnidadeByCity(@Param("municipio") String municipio, @Param("type") String type,@Param("id") Integer id_relatorio);

    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.regiao=:sigla and u.type=:type")
    List<Unidade> getUnidadeBySigla(@Param("sigla") String sigla , @Param("type") String type, @Param("id") Integer id);

    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.revenda=:distribuidora and u.type=:type")
    List<Unidade> getUnidadeByDistro(@Param("distribuidora") String distribuidora,@Param("type") String type , @Param("id") Integer id);

    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.type=:type")
    List<Unidade> getHistorys(@Param("id") Integer id, @Param("type") String type);

    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.data_coleta=:data and u.type=:type")
    List<Unidade> getUnidadeByData(@Param("data") String data, @Param("type") String type , @Param("id") Integer id);

    @Query(value="select valor_venda from unidade where id=:id and municipio=:city",nativeQuery = true)
    String getPriceCompraVenda(@Param("id") Integer id, @Param("city") String city);

    @Query(value="select list_unidade_id from relatorio_list_unidade where relatorio_identifier=:id", nativeQuery=true)
    List<Integer> getIdRelatedRelatorio(@Param("id") Integer relatorio_id);

    @Query(value="delete list_unidade_id from relatorio_list_unidade where list_unidade_id=:id",nativeQuery = true)
    List<Integer> deleteRelatedList(@Param("id") Integer id);


    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.municipio=:city and u.type=:type")
    List<Unidade> getUnidades(@Param("id") Integer id,@Param("type") String type ,@Param("city") String city);


    @Query(value="select u from Unidade u inner join u.relatorio d where d.id=:id and u.bandeira=:bandeira and u.type=:type")
    List<Unidade> getUnidadeByFlag(@Param("bandeira") String bandeira, @Param("type") String type,@Param("id") Integer id_relatorio);

    @Modifying
    @Transactional
    @Query(value="delete from unidade where relatorio_identifier=:id and type=:type",nativeQuery = true)
    void deleteDataRelated(@Param("id") Integer id, @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value="delete from unidade where relatorio_identifier=:id",nativeQuery = true)
    void deleteDataRelatedU(@Param("id") Integer id);
    @Modifying
    @Transactional
    @Query(value="update unidade set  bandeira=:bandeira , codigo_instalacao=:codigo_instalacao ,data_coleta=:data_coleta , estado=:estado, municipio=:municipio ,produto=:produto ,regiao=:regiao ,revenda=:revenda , unidade_medida=:unidade_medida ,valor_compra=:valor_compra , valor_venda=:valor_venda where id=:id",nativeQuery = true)
    Integer updateHistory(@Param("bandeira") String bandeira, @Param("codigo_instalacao") String codigo_instalacao,@Param("data_coleta") String data_coleta,@Param("estado")String estado,@Param("municipio")String municipio,@Param("produto")String produto, @Param("regiao")String regiao,@Param("revenda")String revenda,@Param("unidade_medida") String unidade_medida, @Param("valor_compra") String valor_compra, @Param("valor_venda") String valor_venda, @Param("id") Integer id);





}
