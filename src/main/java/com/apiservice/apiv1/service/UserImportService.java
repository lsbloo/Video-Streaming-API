package com.apiservice.apiv1.service;


import com.apiservice.apiv1.models.User;
import com.apiservice.apiv1.repository.*;
import com.apiservice.apiv1.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImportService {


    private UserRepository userRepository;

    @Autowired
    public UserImportService(UserRepository userRepository){

        this.userRepository=userRepository;
    }




}
