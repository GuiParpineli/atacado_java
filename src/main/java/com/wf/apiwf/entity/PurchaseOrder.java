package com.wf.apiwf.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "purchase_order")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_product"))
    private List<Product> product;

    @OneToOne
    @JoinColumn(name = "id_vendor")
    private Vendor vendor;

    @OneToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @DateTimeFormat(pattern = "dd-mm-yyyy")
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm:ss"));
}
