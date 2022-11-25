package com.wf.apiwf.repository;

import com.wf.apiwf.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByCompanyName(String razaoSocial);

    Optional<Customer> findByCnpj(String cnpj);

    Optional<Customer> findByEmail(String email);
}
