package com.wf.apiwf.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.apiwf.entity.Product;
import com.wf.apiwf.entity.dto.ProductDTO;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.repository.IProductRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IService<Product> {

    private final IProductRepository repository;
    final static Logger log = Logger.getLogger(Product.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ProductService(IProductRepository repository) {this.repository = repository;}


    @Override
    public ResponseEntity<?> getAll() {
        List<Product> saved = repository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        if (saved.isEmpty()) new ResponseEntity<>("None products founded", HttpStatus.NOT_FOUND);
        saved.forEach(c -> productDTOS.add(mapper.convertValue(c, ProductDTO.class)));
        return ResponseEntity.ok(productDTOS);
    }

    @Override
    public ResponseEntity<?> get(Long id) throws ResourceNotFoundException {
        Product saved = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("None Products with id informed founded"));
        ProductDTO dto = mapper.convertValue(saved, ProductDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<?> save(Product product) throws ResourceNotFoundException {
        Product saved;
        try {
            saved = repository.save(product);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error, not saved");
        }
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            log.info("Produto deletado");
        }
        return null;
    }

    @Override
    public ResponseEntity<String> update(Product product) {
        if (product != null && repository.findById(product.getId()).isPresent()) {
            repository.saveAndFlush(product);
            log.info("Produto atualizado com sucesso");
        }
        return null;
    }
}
