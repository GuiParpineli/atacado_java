package com.wf.apiwf.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {

    private String razaoSocial;
    private String nomeFantasia;
    private String email;
    private String cnpj;

}
