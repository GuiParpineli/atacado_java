package com.wf.apiwf.controller;

import com.wf.apiwf.entity.Customer;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/cliente", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/todos")
    public ResponseEntity findAll() { return customerService.getAll();}

    @GetMapping
    public ResponseEntity<?> findById(@PathParam("id") Long id) throws ResourceNotFoundException {
        return customerService.get(id);
    }

    @PostMapping("/adicionar")
    public ResponseEntity addClient(@RequestBody Customer customer) throws ResourceNotFoundException {
        Customer c;
        try {
            c = customerService.save(customer);
        } catch (Exception e) {
            throw new ResourceNotFoundException("nao Cadastrado");
        }
        return ResponseEntity.ok("Salvo com sucesso!");
    }

    @DeleteMapping("/delete")
    public void deletarCliente(@PathVariable("id") Long id) {
        customerService.delete(id);
    }

    @PutMapping("/atualizar")
    public void atualizarCliente(@RequestBody Customer customer) {
        customerService.update(customer);
    }
}
