package com.apiservice.apiv1.dtos;

import com.apiservice.apiv1.models.Unidade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResourceInitials {

    private String message;
    private String description;
    private List<Unidade> unidadeList;
}
