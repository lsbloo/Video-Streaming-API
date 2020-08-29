package com.apiservice.apiv1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {


    @GetMapping(value="")
    public String isOnline(){
        return "App is Online";
    }
}
