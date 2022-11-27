package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.SystemUser;
import com.wf.apiwf.entity.Vendor;
import com.wf.apiwf.exceptions.ResourceNotFoundException;
import com.wf.apiwf.repository.IUserRepository;
import com.wf.apiwf.repository.IVendorRepository;
import com.wf.apiwf.security.SystemUserRoles;
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
    private final IUserRepository userRepository;
    final static Logger log = Logger.getLogger(VendorService.class);
    @Autowired
    public VendorService(IVendorRepository repository, IUserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

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
        userRepository.save(
                SystemUser.builder()
                        .name(vendor.getName())
                        .username(vendor.getEmail())
                        .password(vendor.getPassword())
                        .systemUserRoles(SystemUserRoles.ROLE_VENDOR)
                        .build()
        );
        return ResponseEntity.ok(saved);
    }

    @Override
    public ResponseEntity<?> delete(Long id) throws ResourceNotFoundException {
        Vendor saved = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Error, not found"));
        repository.deleteById(id);
        userRepository.deleteById(
                userRepository.findByEmail(
                        saved.getEmail()).getId());
        log.info("Vendor deleted successfully!");
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> update(Vendor vendor) {
        if (repository.findById(vendor.getId()).isEmpty()) {
            log.info("Vendor not found");
            return new ResponseEntity<>("Vendor not found or not exists", HttpStatus.BAD_REQUEST);
        }
        repository.saveAndFlush(vendor);
        userRepository.saveAndFlush(
                SystemUser.builder()
                        .name(vendor.getName())
                        .username(vendor.getEmail())
                        .password(vendor.getPassword())
                        .build()
        );
        log.info("Updated successfully");
        return ResponseEntity.ok("Updated");
    }
}
