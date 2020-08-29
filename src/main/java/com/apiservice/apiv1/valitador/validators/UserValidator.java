package com.apiservice.apiv1.valitador.validators;

import com.apiservice.apiv1.dtos.UserDTO;
import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.repository.UserRepository;
import com.apiservice.apiv1.valitador.core.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;

/**
 * Simple validator Operation Crud Users;
 */

@Component
public class UserValidator {

    @Autowired
    private UserRepository userRepository;

    /**
     * Verifica a existencia de usuário de acordo com seu username;
     * @return result;
     */
    public Validator<UserDTO> validateExistence(){
        return(result, users) -> {
          try{
              User u = this.userRepository.getUserByUsername(users.getUsername());
              if(u!=null){
                  result.error("error");
              }
          }catch(NullPointerException e){
              result.ok();
          }
        };
    }


    public Validator<User> validateExistenceU(){
        return(result, users) -> {
            try{
                User u = this.userRepository.getUserByUsername(users.getUsername());

                if(u!=null){
                    result.ok();
                }else{
                    result.error("error");
                }
            }catch(NullPointerException e){
                result.error("error");
            }
        };
    }

    public Validator<User> validateExistenceID(){
        return(result, users) -> {
            try{
                User u = this.userRepository.getUserById(users.getId());

                if(u!=null){
                    result.ok();
                }else{
                    result.error("error");
                }
            }catch(NullPointerException e){
                result.error("error");
            }
        };
    }

}
