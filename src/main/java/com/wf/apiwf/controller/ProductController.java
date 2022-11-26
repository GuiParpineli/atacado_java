package com.wf.apiwf.controller;

import com.wf.apiwf.entity.Product;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.service.impl.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/produto", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {this.service = service;}

    private final static Logger log = Logger.getLogger(ProductController.class);


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> findAll() {
        return service.getAll();
    }

    @GetMapping("/id")
    public ResponseEntity<?> findById(@PathParam("id") Long id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> addProduct(@RequestBody Product product) throws ResourceNotFoundException {
        return service.save(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/atualizar")
    public void updateProduct(@RequestBody Product product) {
        service.update(product);
    }

}
