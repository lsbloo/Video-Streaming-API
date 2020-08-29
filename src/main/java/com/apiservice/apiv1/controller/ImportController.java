package com.apiservice.apiv1.controller;


import com.apiservice.apiv1.context.ContextUtil;
import com.apiservice.apiv1.dtos.ResponseUser;
import com.apiservice.apiv1.models.CsvReader;
import com.apiservice.apiv1.service.UserImportService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/import")
@Api(value = "Importação", description = "Importação de arquivo CSV", tags = {"Importação"})
public class ImportController {



    private UserImportService userImportService;

    @Autowired
    public ImportController(UserImportService userImportService){
        this.userImportService=userImportService;
    }




    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Realiza o upload do arquivo csv de um usuário.")
    public ResponseEntity<ResponseUser> uploadCsvFile(@RequestParam("archive") MultipartFile archive, Model model) {
        List<CsvReader> list_reader = new ArrayList<CsvReader>();


        if (archive.isEmpty()) {
            ResponseUser responseUser = new ResponseUser("import failed", "Archive CSV IMPORT EMPTY");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUser);

        } else {
            try {
                File f = multiParFileToFile(archive);
                CSVParser parser = new CSVParserBuilder()
                        .withSeparator('\t')
                        .withIgnoreQuotations(true).build();
                CSVReader csvReader = new CSVReaderBuilder(new FileReader(f))
                        .withSkipLines(1)
                        .withCSVParser(parser)
                        .build();
                String[] next;
                Integer cont = 0;
                while ((next = csvReader.readNext()) != null) {
                    String[] parse = next[0].split("  ");
                    if (parse.length == 11) {
                        CsvReader csvReader1 = new CsvReader();
                        csvReader1.setRegiao(parse[0]);
                        csvReader1.setEstado(parse[1]);
                        csvReader1.setMunicipio(parse[2]);
                        csvReader1.setRevenda(parse[3]);
                        csvReader1.setCodigo_instalacao(parse[4]);
                        csvReader1.setProduto(parse[5]);
                        csvReader1.setData_coleta(parse[6]);
                        if (parse[7].isEmpty()) {
                            csvReader1.setValor_compra(null);
                        } else {
                            csvReader1.setValor_compra(parse[7] + " R$");
                        }
                        if (parse[8].isEmpty()) {
                            csvReader1.setValor_venda(null);
                        } else {
                            csvReader1.setValor_venda(parse[8] + " R$");
                        }
                        csvReader1.setUnidade_medida(parse[9].split("/")[1]);
                        csvReader1.setBandeira(parse[10]);
                        list_reader.add(csvReader1);
                    }
                }
                System.err.println(list_reader.get(0).toString());
                Integer size = list_reader.size();
                ResponseUser responseUser = new ResponseUser("success import", "Quantity imported: " + size + " lines");

                //ImportDataset(list_reader);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  null;
    }


    /**
    public synchronized void ImportDataset(List<CsvReader> list_reader){
        List<Unidade> unidadeList = new ArrayList<>();
        for (CsvReader reader : list_reader){
            unidadeList.add(new Unidade(reader.getRegiao(),reader.getEstado(),reader.getMunicipio(),reader.getRevenda(),reader.getCodigo_instalacao(),reader.getProduto(),reader.getData_coleta(),reader.getValor_compra(),reader.getValor_venda(),reader.getUnidade_medida(),reader.getBandeira()));
        }

        this.userImportService.importDataSet(unidadeList, ContextUtil.getAuthenticationUsername());
    }
     */

    public static File multiParFileToFile(MultipartFile archive) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+archive.getOriginalFilename());
        archive.transferTo(convFile);
        return convFile;
    }


    private String[] returnY(String next){
        if (next.split("/").length != 0){
            String[] y = next.split("/");
            return y;
        }else{
            String[] y = next.split(" ");
            return y;
        }

    }
}



