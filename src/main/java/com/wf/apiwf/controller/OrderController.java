package com.wf.apiwf.controller;

import com.wf.apiwf.entity.PurchaseOrder;
import com.wf.apiwf.service.impl.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
public class OrderController {


    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {this.service = service;}

    private static final Logger log = Logger.getLogger(OrderController.class);

    @GetMapping(value = "/pedidos", produces = "application/json")
    public ResponseEntity<?> buscarTodosPedidos() {
        List<PurchaseOrder> orderSave = service.getAll();
        if(orderSave.isEmpty()){
            return new ResponseEntity(orderSave, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orderSave, HttpStatus.OK);
    }

    @GetMapping(value = "/pedido/{id}")
    public ResponseEntity<?> buscarPedidoPorId(@RequestParam Long id) {
        Optional<PurchaseOrder> orderSave = service.get(id);
        if(orderSave.isEmpty()){
            return new ResponseEntity("Nenhum pedido com o id informado ", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orderSave,HttpStatus.OK);
    }

    @PostMapping("/pedido/cadastrar")
    public ResponseEntity<?> cadastrarPedido(@RequestBody PurchaseOrder purchasePurchaseOrder) {
        PurchaseOrder orderSave = service.save(purchasePurchaseOrder);
        return new ResponseEntity(orderSave, HttpStatus.OK);
    }


    @DeleteMapping("/pedido/delete/{id}")
    public void deletarPedido(@RequestParam Long id) {
        if(service.get(id).isPresent()){
            service.delete(id);
        }
    }

    @PutMapping("/pedido/atualizar")
    public void atualizarPedido(@RequestBody PurchaseOrder purchasePurchaseOrder) {
        if(service.get(purchasePurchaseOrder.getId()).isPresent()){
            service.update(purchasePurchaseOrder);
        }
    }
}
