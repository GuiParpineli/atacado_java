package com.wf.apiwf.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.apiwf.entity.Customer;
import com.wf.apiwf.entity.dto.CustomerDTO;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.repository.ICustomerRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements IService<Customer> {

    private final ICustomerRepository customerRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    final static Logger log = Logger.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) new ResponseEntity<>("No customers registered", HttpStatus.NOT_FOUND);
        customerList.forEach(c -> customerDTOList.add(mapper.convertValue(c, CustomerDTO.class)));
        return ResponseEntity.ok(customerDTOList);
    }

    @Override
    public ResponseEntity<?> get(Long id) throws ResourceNotFoundException {
        Customer saved = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customers with the given id"));
        return new ResponseEntity(saved, HttpStatus.OK);
    }

    @Override
    public Customer save(Customer customer) {
        log.info("customer: " + customer.getRazaoSocial() + " saved successfully");
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Long id) {
        if (customerRepository.findById(id).isPresent()) {
            Optional<Customer> customer = customerRepository.findById(id);
            customerRepository.deleteById(id);
            log.info("Customer deleted successfully!");
        }
    }

    @Override
    public void update(Customer customer) {
        if (customer != null && customerRepository.findById(customer.getId()).isPresent()) {
            customerRepository.saveAndFlush(customer);
            log.info("Updated customer");
        }
    }

    public List<Customer> findByRazaoSocialOrNomeFantasia(String name) {
        return customerRepository.findByRazaoSocial(name);
    }

}