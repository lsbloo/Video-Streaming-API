package com.apiservice.apiv1.controller;

import com.apiservice.apiv1.dtos.JwtResponse;
import com.apiservice.apiv1.models.UserAuth;
import com.apiservice.apiv1.security.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.apiservice.apiv1.service.JwtUserDetailsService;


@RestController
@RequestMapping("/api/v1")
@Api(value="Autenticação", description = "REST API AUTENTICAÇÃO", tags={"Autenticação"})
public class JwtAuthenticationController {


    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailService;


    @Autowired
    public JwtAuthenticationController(AuthenticationManager manager, JwtTokenUtil tokenUtil, JwtUserDetailsService userDetailService){

        this.authenticationManager=manager;
        this.jwtTokenUtil=tokenUtil;
        this.userDetailService=userDetailService;
    }

    @ApiOperation("Realiza o login do usuário, retorna um token de acesso.")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserAuth userAuth) throws Exception {
        authenticate(userAuth.getUsername(), userAuth.getPassword());
        final UserDetails userDetails = this.userDetailService
                .loadUserByUsername(userAuth.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
