package com.apiservice.apiv1.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@NoArgsConstructor
public class Relatorio {

    @Getter @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="identifier")
    private Integer id;

    @Getter @Setter @OneToMany
    private List<Unidade> list_unidade;
}
