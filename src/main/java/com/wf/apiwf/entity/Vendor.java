package com.wf.apiwf.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "VENDOR")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false, unique = false)
    private String name;

    private String lastname;
    @Column(nullable = false,unique = true, length = 12)
    private String cpf;

    @NotNull
    private String email;

    @NotNull
    private String password;
    @Column(nullable = false)
    private int commission;

    public Vendor(String name, String cpf, int commission) {
        this.name = name;
        this.cpf = cpf;
        this.commission = commission;
    }
}
