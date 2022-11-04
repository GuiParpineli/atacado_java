package com.wf.apiwf.entity;


import lombok.*;

import javax.persistence.*;

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
    @Column(nullable = false,unique = true, length = 12)
    private String cpf;
    @Column(nullable = false)
    private int commission;

    public Vendor(String name, String cpf, int commission) {
        this.name = name;
        this.cpf = cpf;
        this.commission = commission;
    }
}
