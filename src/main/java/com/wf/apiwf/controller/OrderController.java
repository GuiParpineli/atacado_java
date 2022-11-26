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
@RequestMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService service;
    private static final Logger log = Logger.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService service) {this.service = service;}

    @GetMapping
    public ResponseEntity<?> findAllOrders() {return service.getAll();}

    @GetMapping(value = "findById")
    public ResponseEntity<?> findOrderById(@RequestParam Long id) throws ResourceNotFoundException {
        return service.get(id);
    }

    @PostMapping("register")
    public ResponseEntity<?> registerOrder(@RequestBody PurchaseOrder purchasePurchaseOrder) throws ResourceNotFoundException {
        return service.save(purchasePurchaseOrder);
    }

    @DeleteMapping("delete")
    public void deleteOrder(@RequestParam Long id) {service.delete(id);}

    @PutMapping("update")
    public void updateOrder(@RequestBody PurchaseOrder purchasePurchaseOrder) {service.update(purchasePurchaseOrder);}
}
