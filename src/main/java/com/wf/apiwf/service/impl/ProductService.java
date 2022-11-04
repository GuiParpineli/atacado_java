package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.Product;
import com.wf.apiwf.repository.IProductRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IService<Product> {

    private final IProductRepository productRepository;
    @Autowired
    public ProductService(IProductRepository productRepository) {this.productRepository = productRepository;}
    final static Logger log = Logger.getLogger(Product.class);


    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> get(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
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
