package com.apiservice.apiv1.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class User implements Serializable, UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="identifier")
    private Integer id;

    @Column(name="name", length = 100)
    private String name;

    @Column(name="last_name", length = 100)
    private String lastName;

    @Column(name="username" , length = 50)
    private String username;

    @Column(name="password", length = 254)
    private String password;


    @Column(name="email", length = 150)
    private String email;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    public User(String username , String password) {
        this.username = username;
        this.password = password;
    }

}
