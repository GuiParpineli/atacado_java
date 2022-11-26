package com.wf.apiwf.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.apiwf.entity.Customer;
import com.wf.apiwf.entity.SystemUser;
import com.wf.apiwf.entity.dto.CustomerDTO;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.repository.ICustomerRepository;
import com.wf.apiwf.repository.IUserRepository;
import com.wf.apiwf.security.SystemUserRoles;
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
    private final IUserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    final static Logger log = Logger.getLogger(CustomerService.class);
    @Autowired
    public CustomerService(ICustomerRepository customerRepository, IUserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<Customer> customerList = customerRepository.findAll();
        customerList.forEach(
                c -> customerDTOList.add(mapper.convertValue(c, CustomerDTO.class)));
        return customerDTOList.isEmpty() ?
                new ResponseEntity<>("No customers registered", HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(customerDTOList);
    }

    @Override
    public ResponseEntity<?> get(Long id) throws ResourceNotFoundException {
        Customer saved = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No customers with the given id"));
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> save(Customer customer) throws ResourceNotFoundException {

        Customer saved;
        try {
            saved = customerRepository.save(customer);
            log.info("customer: " + customer.getCompanyName() + " saved successfully");
        } catch (Exception e) {throw new ResourceNotFoundException("Error, customer not registered");}
        userRepository.save(
                new SystemUser(
                        customer.getCompanyName(),
                        customer.getTradingName(),
                        customer.getEmail(),
                        customer.getPassword(),
                        SystemUserRoles.ROLE_CLIENT
                ));
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> delete(Long id) throws ResourceNotFoundException {
        Optional<Customer> saved = customerRepository.findById(id);
        if (saved.isEmpty()) {
            return new ResponseEntity<>("Customer not found for deleting", HttpStatus.NOT_FOUND);
        }
        log.info("Customer deleted successfully!");
        customerRepository.deleteById(id);
        userRepository.deleteById(
                userRepository.findByUsername(
                        saved.get().getTradingName()).get().getId()
        );
        return new ResponseEntity<>(" Successfully deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> update(Customer customer) {
        if (customerRepository.findById(customer.getId()).isEmpty())
            return new ResponseEntity<>("Customer does not exist", HttpStatus.BAD_REQUEST);
        customerRepository.saveAndFlush(customer);
        userRepository.saveAndFlush(
                SystemUser.builder()
                        .name(customer.getCompanyName())
                        .username(customer.getTradingName())
                        .email(customer.getEmail())
                        .password(customer.password)
                        .systemUserRoles(SystemUserRoles.ROLE_CLIENT)
                        .build()
        );
        log.info("Updated customer");
        return ResponseEntity.ok("Updated");
    }


    public List<Customer> findByCompanyName(String name) {
        return customerRepository.findByCompanyName(name);
    }

}
