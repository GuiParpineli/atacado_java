package com.wf.apiwf.repository;

import com.wf.apiwf.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
