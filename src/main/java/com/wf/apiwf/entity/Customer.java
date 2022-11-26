package com.wf.apiwf.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 20)
    private String cnpj;
    @NotNull
    private String companyName;
    @NotNull
    private String tradingName;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 3, max = 16)
    public String password;


    public Customer(String companyName, String tradingName, String email, SystemUser user, String cnpj) {
        this.companyName = companyName;
        this.tradingName = tradingName;
        this.email = email;
        this.cnpj = cnpj;
    }
}
