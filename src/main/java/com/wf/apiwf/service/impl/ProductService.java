package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.Product;
import com.wf.apiwf.repository.IProductRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IService<Product> {

    private final IProductRepository productRepository;
    @Autowired
    public ProductService(IProductRepository productRepository) {this.productRepository = productRepository;}
    final static Logger log = Logger.getLogger(Product.class);


    @Override
    public ResponseEntity getAll() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<?> get(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> save(Product product) {
        if(product != null){
            log.info("Produto : " + product.getName() + " salvo com sucesso!");
            return productRepository.save(product);
        }
        return new Product();
    }

    @Override
    public void delete(Long id) {
        if(productRepository.findById(id).isPresent()){
            productRepository.deleteById(id);
            log.info("Produto deletado");
        }
    }

    @Override
    public void update(Product product) {
        if(product != null && productRepository.findById(product.getId()).isPresent()){
            productRepository.saveAndFlush(product);
            log.info("Produto atualizado com sucesso");
        }
    }
}
