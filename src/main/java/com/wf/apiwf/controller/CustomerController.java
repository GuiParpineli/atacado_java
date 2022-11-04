package com.wf.apiwf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.apiwf.entity.Customer;
import com.wf.apiwf.entity.dto.CustomerDTO;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.service.impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity bucarTodosClientes() throws ResourceNotFoundException {
        List<Customer> customerList;
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        customerList = customerService.getAll();
        if (customerList.isEmpty()) {
            return new ResponseEntity("Nenhum Cliente cadastrado", HttpStatus.NOT_FOUND);
        }
        for (Customer c : customerList) {
            customerDTOList.add(mapper.convertValue(c, CustomerDTO.class));
        }
        return ResponseEntity.ok(customerList);
    }

    @GetMapping
    public Optional<Customer> buscarClientepoId(@PathParam("id") Long id) {
        return customerService.get(id);
    }

    @PostMapping("/adicionar")
    public ResponseEntity addCliente(@RequestBody Customer customer) throws ResourceNotFoundException {
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
