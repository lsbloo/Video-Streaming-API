package com.apiservice.apiv1.service;


import com.apiservice.apiv1.dtos.UserDTO;
import com.apiservice.apiv1.dtos.UserGDTO;
import com.apiservice.apiv1.dtos.UserGGDTO;
import com.apiservice.apiv1.interfaces.UserInterfaceCrud;
import com.apiservice.apiv1.models.Relatorio;
import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.repository.RelatorioRepository;
import com.apiservice.apiv1.repository.UnidadeRepository;
import com.apiservice.apiv1.repository.UserRepository;
import com.apiservice.apiv1.valitador.core.Result;
import com.apiservice.apiv1.valitador.core.ValidatorBuilder;
import com.apiservice.apiv1.valitador.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterfaceCrud {

    private UserValidator userValidator;

    private UserRepository userRepository;
    private RelatorioRepository relatorioRepository;
    private UnidadeRepository unidadeRepository;

    @Autowired
    public UserService(UserValidator userValidator, UserRepository userRepository, RelatorioRepository relatorioRepository, UnidadeRepository unidadeRepository){
        this.userValidator=userValidator;
        this.userRepository = userRepository;
        this.relatorioRepository=relatorioRepository;
        this.unidadeRepository=unidadeRepository;
    }


    public List<UserGGDTO> findAllUsers(){
        Iterable<User> optionalUser = this.userRepository.findAll();
        List<UserGGDTO> users = new ArrayList<>();
        for(User u : optionalUser){
            UserGGDTO userGDTO = new UserGGDTO();
            userGDTO.setEmail(u.getEmail());
            userGDTO.setLastName(u.getLastName());
            userGDTO.setName(u.getName());
            userGDTO.setUsername(u.getUsername());
            userGDTO.setId(u.getId());
            users.add(userGDTO);
        }
        return users;
    }
    /**
     * creates a new bcryptpassword object and encrypts the user's password.
     *
     * @return new instance of BcryptPasswordEncoder;
     */
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public boolean createUser(UserDTO userDTO){

        Result result = new ValidatorBuilder<UserDTO>().
                apply(userValidator.validateExistence()).validate(userDTO);

        if(result.ok()){
            User u = new User();
            u.setEmail(userDTO.getEmail());
            u.setLastName(userDTO.getLastName());
            u.setPassword(userDTO.getPassword());
            String pass = u.getPassword();
            u.setPassword(passwordEncoder().encode(pass));
            u.setUsername(userDTO.getUsername());
            u.setName(userDTO.getUsername());
            Integer id_u = this.userRepository.save(u).getId();

            Relatorio relatorio = new Relatorio();
            Integer id  = this.relatorioRepository.save(relatorio).getId();
            this.userRepository.updateRelatorioId(id, id_u);

            return true;
        }
        return false;
    }
    @Override
    public boolean updateUser(@NotNull  UserDTO userDTO , Integer id_user){
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder().encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());

        Optional<User> u = this.userRepository.findById(id_user);
        if(u != null){
            Integer re = this.userRepository.updateUsers(user.getEmail(), user.getUsername(), user.getName(), user.getLastName(), user.getPassword(), id_user);
            if (re != 0) return true;

        }
        return false;
    }
    @Override
    public boolean deleteUser(Integer id_user){
        try {
            User user = this.userRepository.getUserById(id_user);
            if(user!= null){
                this.unidadeRepository.deleteDataRelatedU(user.getRelatorio().getId());
                this.userRepository.delete(user);
                this.relatorioRepository.delete(user.getRelatorio());
                return true;
            }
            return false;
        }catch (NullPointerException e){
            return false;
        }
    }

    @Override
    public UserGDTO getUserById(Integer id_user) {
        try{
            User u = this.userRepository.getUserById(id_user);
            if(u!=null){
                UserGDTO dto = new UserGDTO();
                dto.setUsername(u.getUsername());
                dto.setLastName(u.getLastName());
                dto.setName(u.getName());
                dto.setEmail(u.getEmail());
                return dto;
            }
        }catch (NullPointerException e){
            return null;
        }
        return null;
    }


    public boolean validateIdentifier(String identifier){
        char[] chart = identifier.toCharArray();
        for (int i = 0 ; i < chart.length ; i ++){
            if(Character.isDigit( chart[i] )){
                return true;
            }
        }

        return false;
    }



}
