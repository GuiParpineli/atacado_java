package com.wf.apiwf.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;

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
    private String razaoSocial;
    @NotNull
    private String nomeFantasia;
    @NotNull
    private String email;
   @OneToOne
   @JoinColumn(name = "id_user")
   private SystemUser user;

    public Customer(String razaoSocial, String nomeFantasia, String email, SystemUser user, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.email = email;
        this.user = user;
        this.cnpj = cnpj;
    }
}
