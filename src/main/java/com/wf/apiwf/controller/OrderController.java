package com.wf.apiwf.controller;

import com.wf.apiwf.entity.PurchaseOrder;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.service.impl.OrderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {


    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {this.service = service;}

    private static final Logger log = Logger.getLogger(OrderController.class);

    @GetMapping(value = "pedidos", produces = "application/json")
    public ResponseEntity<?> findAllOrders() {return service.getAll();}

    @GetMapping(value = "pedido/{id}")
    public ResponseEntity<?> findOrderById(@RequestParam Long id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping("pedido/cadastrar")
    public ResponseEntity<?> registerOrder(@RequestBody PurchaseOrder purchasePurchaseOrder) throws ResourceNotFoundException {
        return service.save(purchasePurchaseOrder);
    }


    @DeleteMapping("/pedido/delete/{id}")
    public void deleteOrder(@RequestParam Long id) {service.delete(id);}

    @PutMapping("/pedido/atualizar")
    public void atualizarPedido(@RequestBody PurchaseOrder purchasePurchaseOrder) { service.update(purchasePurchaseOrder); }
}
