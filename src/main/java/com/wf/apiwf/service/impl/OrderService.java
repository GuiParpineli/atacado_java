package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.PurchaseOrder;
import com.wf.apiwf.repository.IOrderRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IService<PurchaseOrder> {


    private final IOrderRepository purchaseOrderRepository;

    @Autowired
    public OrderService(IOrderRepository purchaseOrderRepository) {this.purchaseOrderRepository = purchaseOrderRepository;}
    final static Logger log = Logger.getLogger(OrderService.class);



    @Override
    public List<PurchaseOrder> getAll() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public Optional<PurchaseOrder> get(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public PurchaseOrder save(PurchaseOrder purchasePurchaseOrder) {
        if (purchasePurchaseOrder != null) {
            log.info("Efetuando pedido");
            return purchaseOrderRepository.save(purchasePurchaseOrder);
        }
        return new PurchaseOrder();
    }

    @Override
    public void delete(Long id) {
        if (purchaseOrderRepository.findById(id).isPresent()) {
            purchaseOrderRepository.deleteById(id);
            log.info("Pedido excluido");
        }
    }

    @Override
    public void update(PurchaseOrder purchasePurchaseOrder) {
        if (purchasePurchaseOrder != null && purchaseOrderRepository.findById(purchasePurchaseOrder.getId()).isPresent()) {
            purchaseOrderRepository.saveAndFlush(purchasePurchaseOrder);
            log.info("Pedido Alterado");
        }
    }

}
