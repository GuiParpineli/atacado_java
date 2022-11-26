package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.Vendor;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.repository.IVendorRepository;
import com.wf.apiwf.service.IService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService implements IService<Vendor> {

    private final IVendorRepository repository;

    @Autowired
    public VendorService(IVendorRepository repository) {this.repository = repository;}

    final static Logger log = Logger.getLogger(VendorService.class);

    @Override
    public ResponseEntity<?> getAll() {
        List<Vendor> saved = repository.findAll();
        return saved.isEmpty() ? new ResponseEntity<>("None vendor founded", HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> get(Long id) throws ResourceNotFoundException {
        Vendor saved = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("None vendors found " +
                "with id informed"));
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> save(Vendor vendor) throws ResourceNotFoundException {
        Vendor saved;
        try {
            saved = repository.save(vendor);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error, not saved");
        }
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            log.info("Vendedor Deletado com sucesso!");
        }

        return null;
    }

    @Override
    public ResponseEntity<String> update(Vendor vendor) {
        if (vendor != null && repository.findById(vendor.getId()).isPresent()) {
            repository.saveAndFlush(vendor);
            log.info("Vendedor cadastrado!");
        }
        return null;
    }

}
