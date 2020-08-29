package com.apiservice.apiv1.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@Entity
public class StreamM3 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="identifier")
    private Integer id;


    @Column(name="path_url")
    private String path_url;

}
