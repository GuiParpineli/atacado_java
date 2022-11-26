package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.OrderStatus;
import com.wf.apiwf.entity.PurchaseOrder;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.repository.IOrderRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import state.IOrderState;

import java.util.List;

@Service
public class OrderService implements IService<PurchaseOrder>, IOrderState {
    private final IOrderRepository repository;
    final static Logger log = Logger.getLogger(OrderService.class);

    @Autowired
    public OrderService(IOrderRepository repository) {this.repository = repository;}

    @Override
    public ResponseEntity<?> getAll() {
        List<PurchaseOrder> saved = repository.findAll();
        return saved.isEmpty() ? new ResponseEntity<>(saved, HttpStatus.OK) : ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> get(Long id) throws ResourceNotFoundException {
        PurchaseOrder saved = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> save(PurchaseOrder purchasePurchaseOrder) throws ResourceNotFoundException {
        PurchaseOrder saved;
        try {saved = repository.save(purchasePurchaseOrder);} catch (Exception e) {
            throw new ResourceNotFoundException("Error, order not saved");
        }
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (repository.findById(id).isEmpty())
            return new ResponseEntity<>("Error, order not found", HttpStatus.NOT_FOUND);

        repository.deleteById(id);
        log.info("Order deleted");
        return ResponseEntity.ok("Successfully deleted");
    }

    @Override
    public ResponseEntity<String> update(PurchaseOrder purchasePurchaseOrder) {
        if (purchasePurchaseOrder.getStatus().equals(OrderStatus.OPEN)) {
            if (repository.findById(purchasePurchaseOrder.getId()).isPresent()) {
                repository.saveAndFlush(purchasePurchaseOrder);
                log.info("Order updated");
            }
        }
        return ResponseEntity.ok("Order updated");
    }

    @Override
    public void previousStatus() {

    }

    @Override
    public void nextStatus() {

    }

    @Override
    public void issueOrder() {

    }

    @Override
    public void finishOrder() {

    }
}
