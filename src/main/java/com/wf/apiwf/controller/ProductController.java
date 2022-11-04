package com.wf.apiwf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wf.apiwf.entity.Product;
import com.wf.apiwf.entity.dto.ProductDTO;
import com.wf.apiwf.service.impl.ProductService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/produto", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {this.productService = productService;}

    private final static Logger log = Logger.getLogger(ProductController.class);


    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public List<Product> buscarTodosProdutos() {
        return productService.getAll();
    }

    @GetMapping("/id")
    public ResponseEntity buscarProdutoPorId(@PathParam("id") Long id) {

        ObjectMapper mapper = new ObjectMapper();

        Optional<Product> productOptional = productService.get(id);
        if (productOptional.isEmpty()) {
            return new ResponseEntity("Produto não foi encontrado", HttpStatus.NOT_FOUND);
        }


        ProductDTO productDTO = mapper.convertValue(productOptional, ProductDTO.class);

        return new ResponseEntity(productDTO, HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public Product addProduto(@RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/delete/{id}")
    public void deletarProduto(@PathVariable Long id) {
        productService.delete(id);
    }

    @PutMapping("/atualizar")
    public void alterarProduto(@RequestBody Product product) {
        productService.update(product);
    }

}
