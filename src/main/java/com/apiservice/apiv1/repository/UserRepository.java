package com.apiservice.apiv1.repository;


import com.apiservice.apiv1.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {



    @Query(value="select * from user where username=:username", nativeQuery =true)
    User getUserByUsername(@Param("username") String username);

    @Query(value="select * from user where identifier=:id", nativeQuery =true)
    User getUserById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value="update user set email=:email, username=:username, password=:password, name=:name, last_name=:lastname where identifier=:id",nativeQuery = true)
    Integer updateUsers(@Param("email") String email, @Param("username") String username,
                       @Param("name") String name,@Param("lastname") String lastName, @Param("password") String password, @Param("id") Integer id);


    @Modifying
    @Transactional
    @Query(value="update user set relatorio_identifier=:id where identifier=:user_id", nativeQuery = true)
    Integer updateRelatorioId(@Param("id") Integer id_relatorio, @Param("user_id") Integer user_identifier);


}
