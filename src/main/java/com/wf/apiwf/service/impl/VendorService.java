package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.Vendor;
import com.wf.apiwf.repository.IVendorRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VendorService implements IService<Vendor> {

    private final IVendorRepository vendorRepository;
    @Autowired
    public VendorService(IVendorRepository vendorRepository) {this.vendorRepository = vendorRepository;}

    final static Logger log = Logger.getLogger(VendorService.class);



    @Override
    public ResponseEntity getAll() {
        return vendorRepository.findAll();
    }

    @Override
    public ResponseEntity<?> get(Long id) {
        return vendorRepository.findById(id);
    }

    @Override
    public ResponseEntity<?> save(Vendor vendor) {
        if (vendor != null) {
            log.info("Vendedor: " + vendor.getName() + " salvo com sucesso!");
            return vendorRepository.save(vendor);
        }
        return new Vendor();
    }

    @Override
    public void delete(Long id) {
        if (vendorRepository.findById(id).isPresent()) {
            vendorRepository.deleteById(id);
            log.info("Vendedor Deletado com sucesso!");
        }

    }

    @Override
    public void update(Vendor vendor) {
        if (vendor != null && vendorRepository.findById(vendor.getId()).isPresent()) {
            vendorRepository.saveAndFlush(vendor);
            log.info("Vendedor cadastrado!");
        }
    }

}
