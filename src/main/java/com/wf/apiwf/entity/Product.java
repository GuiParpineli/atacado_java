package com.wf.apiwf.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = false, length = 150)
    private String name;

    private String providerName;
    @Column(length = 50)
    private String ncm;
    @Column(length = 150)
    private String category;
    @Column(nullable = false)
    private int inventory;
    @Column(nullable = false, length = 155)
    private double pricePurchase;
    @Column(nullable = false, length = 155)
    private double priceSale;

}
