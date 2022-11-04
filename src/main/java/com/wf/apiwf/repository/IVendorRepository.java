package com.wf.apiwf.repository;

import com.wf.apiwf.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVendorRepository extends JpaRepository<Vendor,Long> {
}
