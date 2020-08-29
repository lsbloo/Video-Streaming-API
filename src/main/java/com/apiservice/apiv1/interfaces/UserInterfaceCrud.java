package com.apiservice.apiv1.interfaces;

import com.apiservice.apiv1.dtos.UserDTO;
import com.apiservice.apiv1.dtos.UserGDTO;

import com.apiservice.apiv1.models.User;

import javax.validation.constraints.NotNull;

public interface UserInterfaceCrud {

    /**
     *
     * @param userDTO
     * @return
     */
    boolean createUser(UserDTO userDTO);

    /**
     *
     * @param userDTO
     * @return
     */
    boolean updateUser(@NotNull UserDTO userDTO,  Integer id_user);

    /**
     *
     * @param id_user
     * @return
     */
    boolean deleteUser(Integer id_user);

    /**
     *
     * @param id_user
     * @return
     */
    UserGDTO getUserById(Integer id_user);

}
