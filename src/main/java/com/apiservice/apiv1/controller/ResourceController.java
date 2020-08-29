package com.apiservice.apiv1.controller;

import com.apiservice.apiv1.context.ContextUtil;
import com.apiservice.apiv1.dtos.ResourceInitials;
import com.apiservice.apiv1.dtos.ResourcePriceCityDTO;
import com.apiservice.apiv1.dtos.ResponsePriceCity;
import com.apiservice.apiv1.dtos.UnidadeResponseDTO;
import com.apiservice.apiv1.models.Unidade;
import com.apiservice.apiv1.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resource")
@Api(value = "Recursos", description = "Recursos de informações detalhadas", tags = {"Recursos"})
public class ResourceController {


    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService){

        this.resourceService=resourceService;
    }
    @GetMapping(value="/price/flag", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "retorna o valor médio do valor da compra e do valor da venda pela bandeira.")
    public ResponseEntity<ResponsePriceCity> getDetailsMediaFlag(@RequestParam("bandeira") String bandeira, @RequestParam("type") String type_data){
        if(bandeira.isEmpty()){
            ResponsePriceCity dto = new ResponsePriceCity();
            dto.setMessage("fail");
            dto.setDescription("Parameter municipio is NULL");
            dto.setMedia_price_compra(null);
            dto.setMedia_price_venda(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        }else{
            Double[] result = this.resourceService.getMediaPricesByFlag(bandeira,type_data,ContextUtil.getAuthenticationUsername());
            if(result!=null){
                ResponsePriceCity dto = new ResponsePriceCity();
                dto.setMessage("success");
                dto.setDescription("");
                dto.setMedia_price_compra(String.format("%.2f",result[1]) + " R$");
                dto.setMedia_price_venda(String.format("%.2f",result[0]) + " R$");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);

            }else{
                ResponsePriceCity dto = new ResponsePriceCity();
                dto.setMessage("fail");
                dto.setDescription("Dont found media prices, try other flag..");
                dto.setMedia_price_compra(null);
                dto.setMedia_price_venda(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            }
        }
    }
    @GetMapping(value="/price/city", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="retorna o valor médio do valor da compra e do valor da venda pelo municipio.")
    public ResponseEntity<ResponsePriceCity> getDetailsMediaPricesCity(@RequestParam("municipio") String municipio, @RequestParam("type") String type_data){

        if(municipio.isEmpty()){
            ResponsePriceCity dto = new ResponsePriceCity();
            dto.setMessage("fail");
            dto.setDescription("Parameter municipio is NULL");
            dto.setMedia_price_compra(null);
            dto.setMedia_price_venda(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        }else{
            Double[] result = this.resourceService.getMediaPricesByCity(municipio,type_data,ContextUtil.getAuthenticationUsername());
            if(result!=null){
                ResponsePriceCity dto = new ResponsePriceCity();
                dto.setMessage("success");
                dto.setDescription("");
                dto.setMedia_price_compra(String.format("%.2f",result[1]) + " R$");
                dto.setMedia_price_venda(String.format("%.2f",result[0]) + " R$");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);

            }else{
                ResponsePriceCity dto = new ResponsePriceCity();
                dto.setMessage("fail");
                dto.setDescription("Dont found media prices, try other city.");
                dto.setMedia_price_compra(null);
                dto.setMedia_price_venda(null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            }
        }
    }


    @GetMapping(value="/data", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="retorna informações agrupadas por data de coleta")
    public ResponseEntity<Object> getDetailsData(@RequestParam("data") String data, @RequestParam("type") String type_data, @RequestParam("page") int page, @RequestParam("size") int size) {
        if (data.isEmpty()) {
            ResourceInitials dto = new ResourceInitials();
            dto.setMessage("fail");
            dto.setDescription("Failed to search, Parameter is NULL");
            dto.setUnidadeList(null);
        } else {
            List<UnidadeResponseDTO> unidadeList = this.resourceService.getConjuntByData(data, type_data,ContextUtil.getAuthenticationUsername());
            if (unidadeList.size() == 0) {
                ResourceInitials dto = new ResourceInitials();
                dto.setMessage("fail");
                dto.setDescription("Quantity Itens Found: " + unidadeList.size());
                dto.setUnidadeList(null);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            } else {
                PageRequest pageRequest = PageRequest.of(page, size);
                Pageable pageable = pageRequest;

                int start = (int) pageable.getOffset();
                int end = (start + pageable.getPageSize()) > unidadeList.size() ? unidadeList.size() : (start + pageable.getPageSize());

                Page<UnidadeResponseDTO> unidadePage =
                        new PageImpl<UnidadeResponseDTO>(unidadeList.subList(start, end), pageable, unidadeList.size());

                System.err.println(unidadePage.getTotalPages());

                return new ResponseEntity<>(unidadePage, HttpStatus.ACCEPTED);
            }
        }
        return null;
    }


    @GetMapping(value="/distributor", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="retorna informações agrupadas por distribuidora/revenda")
    public ResponseEntity<Object> getDetailsDistributor(@RequestParam("distribuidora") String distribuidora, @RequestParam("type") String type_data,@RequestParam("page") int page, @RequestParam("size") int size){
        if(distribuidora.isEmpty()){
            ResourceInitials dto = new ResourceInitials();
            dto.setMessage("fail");
            dto.setDescription("Failed to search, Parameter is NULL");
            dto.setUnidadeList(null);
        }
        else{
            List<UnidadeResponseDTO> unidadeList = this.resourceService.getConjuntByDistributor(distribuidora,type_data,ContextUtil.getAuthenticationUsername());
            if(unidadeList.size() == 0){
                ResourceInitials dto = new ResourceInitials();
                dto.setMessage("fail");
                dto.setDescription("Quantity Itens Found: " + unidadeList.size());
                dto.setUnidadeList(null);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            }else{
                PageRequest pageRequest = PageRequest.of(page,size);
                Pageable pageable = pageRequest;

                int start = (int) pageable.getOffset();
                int end = (start + pageable.getPageSize()) > unidadeList.size() ? unidadeList.size() : (start + pageable.getPageSize());

                Page<UnidadeResponseDTO> unidadePage =
                        new PageImpl<UnidadeResponseDTO>(unidadeList.subList(start,end),pageable,unidadeList.size());

                System.err.println(unidadePage.getTotalPages());

                return new ResponseEntity<>(unidadePage,HttpStatus.ACCEPTED);
            }

        }
        return null;
    }
    @GetMapping(value="/initials", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="retorna informações agrupadas por sigla")
    public ResponseEntity<Object> getDetailsInitials(@RequestParam("sigla") String sigla, @RequestParam("type") String type_data,@RequestParam("page") int page , @RequestParam("size") int size){
        if(sigla.isEmpty()){
            ResourceInitials dto = new ResourceInitials();
            dto.setMessage("fail");
            dto.setDescription("Failed to search, Parameter is NULL");
            dto.setUnidadeList(null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        }else{
            List<UnidadeResponseDTO> unidadeList = this.resourceService.getConjuntUnidadesByInitial(sigla,type_data,ContextUtil.getAuthenticationUsername());
            if(unidadeList.size() != 0){
                ResourceInitials dto = new ResourceInitials();
                dto.setMessage("success");
                dto.setDescription("Quantity Itens Found: " + unidadeList.size());

                PageRequest pageRequest = PageRequest.of(page,size);
                Pageable pageable = pageRequest;

                int start = (int) pageable.getOffset();
                int end = (start + pageable.getPageSize()) > unidadeList.size() ? unidadeList.size() : (start + pageable.getPageSize());

                Page<UnidadeResponseDTO> unidadePage =
                        new PageImpl<UnidadeResponseDTO>(unidadeList.subList(start,end),pageable,unidadeList.size());

                System.err.println(unidadePage.getTotalPages());

                return new ResponseEntity<>(unidadePage,HttpStatus.ACCEPTED);

            }else{
                ResourceInitials dto = new ResourceInitials();
                dto.setMessage("fail");
                dto.setDescription("Quantity Itens Found: " + unidadeList.size());
                dto.setUnidadeList(null);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            }
        }

    }

    @GetMapping(value="/price", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna a media do preço do combustivel dado um nome do municipio.")
    public ResponseEntity<ResourcePriceCityDTO> getMediaPriceByCity(@RequestParam("nome_municipio") String nome_municipio,@RequestParam("type") String type_data){

        if(nome_municipio.isEmpty()){
            ResourcePriceCityDTO dto = new ResourcePriceCityDTO();
            dto.setMessage("Fail");
            dto.setDescription("Failed to search price by city. Parameter is NULL");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
        }else{
            Double media = this.resourceService.getMediaPriceByCity(nome_municipio, type_data,ContextUtil.getAuthenticationUsername());
            if (media == 0.0){
                ResourcePriceCityDTO dto = new ResourcePriceCityDTO();
                dto.setMessage("Fail");
                dto.setDescription("Failed to search price by city. Media Price dont found.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);
            }else{
                ResourcePriceCityDTO dto = new ResourcePriceCityDTO();
                dto.setMessage("success");
                dto.setDescription("");
                dto.setCity(nome_municipio);
                dto.setPrice(String.format("%.2f", media) + " R$");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(dto);
            }
        }
    }
}
