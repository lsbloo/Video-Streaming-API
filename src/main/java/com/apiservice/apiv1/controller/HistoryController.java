package com.apiservice.apiv1.controller;


import com.apiservice.apiv1.context.ContextUtil;
import com.apiservice.apiv1.dtos.*;
import com.apiservice.apiv1.service.HistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@Api(value = "Histórico", description = "Crud Histórico preço combustivel", tags = {"Histórico"})
public class HistoryController {

    private HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService){
        this.historyService=historyService;
    }

    @PostMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Permite a criação do histórico do preço combustivel.")
    public ResponseEntity<ResponseHistory> createHistory(@NotNull @RequestBody HistoryCreate dto){

        boolean result = this.historyService.createHistory(dto, ContextUtil.getAuthenticationUsername());
        if(result){
            ResponseHistory responseHistory = new ResponseHistory();
            responseHistory.setDescription("history created successful");
            responseHistory.setMessage("OK");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseHistory);
        }
        ResponseHistory responseHistory = new ResponseHistory();
        responseHistory.setDescription("history dont created, is already inserted.");
        responseHistory.setMessage("FAIL");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHistory);
    }
    @PutMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Atualiza um historico especificado")
    public ResponseEntity<ResponseHistory> updateHistory(@NotNull @RequestBody HistoryCreate dto, @RequestParam("id_history") Integer id_history) {
        boolean result = this.historyService.updateHistory(dto,id_history);
        if(result){
            ResponseHistory responseHistory = new ResponseHistory();
            responseHistory.setDescription("history updated successful");
            responseHistory.setMessage("OK");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseHistory);
        }
        ResponseHistory responseHistory = new ResponseHistory();
        responseHistory.setDescription("history dont update, dont have history object by id");
        responseHistory.setMessage("FAIL");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHistory);

    }


    @DeleteMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Deleta um histórico especificado.")
    public ResponseEntity<ResponseHistory> deleteHistory(@NotNull @RequestParam("id_history") Integer id_history) {

        boolean result = this.historyService.deleteHistory(id_history);
        if(result){
            ResponseHistory responseHistory = new ResponseHistory();
            responseHistory.setDescription("history deleted successful");
            responseHistory.setMessage("OK");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseHistory);
        }
        ResponseHistory responseHistory = new ResponseHistory();
        responseHistory.setDescription("history dont deleted, dont have history object by id");
        responseHistory.setMessage("FAIL");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHistory);
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Retorna um historico especificado.")
    public ResponseEntity<ResponseHistoryGet> getHistoryById(@NotNull @RequestParam("id_history") Integer id_history) {

        UnidadeResponseDTO result = this.historyService.getHistory(id_history);
        if(result != null){
            ResponseHistoryGet responseHistoryGet = new ResponseHistoryGet();
            responseHistoryGet.setMessage("OK");
            responseHistoryGet.setDescription("History found!");
            responseHistoryGet.setHistory(result);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseHistoryGet);
        }
        ResponseHistoryGet responseHistoryGet = new ResponseHistoryGet();
        responseHistoryGet.setMessage("Fail");
        responseHistoryGet.setDescription("History dont found!");
        responseHistoryGet.setHistory(null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseHistoryGet);

    }

    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Retorna todos os historicos de um usuário especificado")
    public List<UnidadeResponseDTOID> getHistorys(){
        return this.historyService.getHistorys();
    }
}
