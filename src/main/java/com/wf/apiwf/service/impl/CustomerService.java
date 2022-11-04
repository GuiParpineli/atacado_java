package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.Customer;
import com.wf.apiwf.repository.ICustomerRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements IService<Customer> {

    private final ICustomerRepository customerRepository;

    final static Logger log = Logger.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(ICustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> get(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        log.info("cliente: " + customer.getRazaoSocial() + " salvo com sucesso");
        return  customerRepository.save(customer);
    }

    @Override
    public void delete(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            Optional<Customer> customer = customerRepository.findById(id);
            customerRepository.deleteById(id);
            log.info("Cliente deletado com sucesso!");
        }
    }

    @Override
    public void update(Customer customer) {
        if (customer != null && customerRepository.findById(customer.getId()).isPresent()) {
            customerRepository.saveAndFlush(customer);
            log.info("Cliente Atualizado");
        }
    }

    public List<Customer> findByRazaoSocialOrNomeFantasia(String name) {
        return customerRepository.findByRazaoSocial(name);
    }

    ;

}
