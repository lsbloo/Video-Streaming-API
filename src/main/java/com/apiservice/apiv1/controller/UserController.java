package com.apiservice.apiv1.controller;


import com.apiservice.apiv1.dtos.UserDTO;
import com.apiservice.apiv1.dtos.UserGDTO;
import com.apiservice.apiv1.dtos.UserGGDTO;
import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.apiservice.apiv1.dtos.ResponseUser;

import java.util.List;


/**
 * @author osvaldoairon
 *
 * Classe Controller Responsavel pelo CRUD de usuários.
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(value="Usuários", description = "CRUD Usuários", tags= {"Usuários"})
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;

    }

    @ApiOperation(value="Retorna todos os usuários")
    @GetMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUsers(){
        List<UserGGDTO> result = this.userService.findAllUsers();
        if(result.size() == 0 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserFindFail());
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
    @ApiOperation(value="Permite a criação de um usuário")
    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseUser> createSimpleUser(@RequestBody UserDTO userDTO){

        boolean result = this.userService.createUser(userDTO);
        if(result){
            return ResponseEntity.status(HttpStatus.CREATED).body(responseUserCreatedOK());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserCreatedFail());
        }
    }

    /**
     * realiza a edição de um usuário de acordo com seu username;
     *
     * @return
     */
    @PutMapping(value="",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Atualiza um usuário.")
    public ResponseEntity<ResponseUser> updateSimpleUser(@RequestBody UserDTO userDTO, @RequestParam Integer id_user){
        boolean result = this.userService.updateUser(userDTO, id_user);

        if(result){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUserUpdateOK());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserUpdateFail());
        }

    }
    @DeleteMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Deleta um usuário.")
    public ResponseEntity<ResponseUser> deleteSimpleUser(@RequestParam Integer id){
        boolean result = this.userService.deleteUser(id);

        if(result){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseUserDeleteOK());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseUserDeleteFail());
        }
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="Retorna um usuário.")
    public ResponseEntity<UserGDTO> getSimpleUser(@RequestParam Integer id){

        UserGDTO u  = this.userService.getUserById(id);
        if(u!=null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(u);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public ResponseUser responseUserUpdateFail(){
        return new ResponseUser("fail", "User dont updated");
    }
    public ResponseUser responseUserUpdateOK(){
        return new ResponseUser("success", "User updated successful");
    }
    public ResponseUser responseUserCreatedFail(){
        return new ResponseUser("fail", "User dont created,there is a user with this username .Please try again");
    }
    public ResponseUser responseUserCreatedOK(){
        return new ResponseUser("success","User created successful");
    }

    public ResponseUser responseUserDeleteOK(){
        return new ResponseUser("success", "User deleted successful");
    }
    public ResponseUser responseUserDeleteFail(){
        return new ResponseUser("fail", "User dont updated, try again");
    }
    public ResponseUser responseUserFindFail(){
        return new ResponseUser("fail", "Users dont found, try again");
    }
}
