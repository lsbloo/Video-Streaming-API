package com.apiservice.apiv1.controller;

import com.apiservice.apiv1.dtos.StreamDTO;
import com.apiservice.apiv1.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.http.server.ServerHttpRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/resource")
@Api(value = "Recursos", description = "Recursos de video stream e url de streams m3u8", tags = {"Recursos"})
public class ResourceController {


    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService){

        this.resourceService=resourceService;
    }

    @ApiOperation(value="Retorna uma url de uma stream m3u8 especificada pelo nome.")
    @GetMapping("/stream")
    public ResponseEntity<StreamDTO> getStreamUrl(@NotNull  @RequestParam("fileName") String fileName){
        return this.resourceService.getStreamByName(fileName);
    }


    @ApiOperation(value="Renderiza um video stream fornecido no servidor, especificado pelo tipo do video ex .mp4 e pelo seu nome")
    @GetMapping("/video/stream/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName) {

        return Mono.just(resourceService.prepareContent(fileName, fileType, httpRangeList));

    }


}
