package com.wf.apiwf.controller;

import com.wf.apiwf.entity.Vendor;
import com.wf.apiwf.repository.IVendorRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/vendedor", produces = MediaType.APPLICATION_JSON_VALUE)
public class VendorController {

    private final IVendorRepository vendorRepository;

    @Autowired
    public VendorController(IVendorRepository vendorRepository) {this.vendorRepository = vendorRepository;}

    private static Logger log = Logger.getLogger(VendorController.class);


    @GetMapping
    public ResponseEntity buscarTodosVendedores() {
        List<Vendor> vendorSave = vendorRepository.findAll();
        if (vendorSave.isEmpty()) {
            return new ResponseEntity("Nenhum vendedor cadastrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(vendorSave, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity buscarVendedorId(@PathParam("id") Long id) {
        Optional<Vendor> vendorSave = vendorRepository.findById(id);
        if (vendorSave.isEmpty()) {
            return new ResponseEntity("Nenhum vendedor encontrado", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(vendorSave, HttpStatus.OK);
    }

    @PostMapping("adicionar")
    public ResponseEntity addVendedor(@RequestBody Vendor vendor) {
        Vendor vendorSave = vendorRepository.save(vendor);
        log.info("Vendedor" + vendor.getName() + "salvo com sucesso");
        return new ResponseEntity(vendorSave, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public void removerVendedor(@PathParam("id") Long id) {
        if (vendorRepository.findById(id).isPresent()) {
            vendorRepository.deleteById(id);
            log.info("Vendedor" + vendorRepository.findById(id).get().getName() + "deletado com sucesso!");
        }
    }

    @PutMapping("/atualizar")
    public void atualizarVendedor(@RequestBody Vendor vendor) {
        if (vendorRepository.findById(vendor.getId()).isPresent()) {
            vendorRepository.saveAndFlush(vendor);
        }
    }

}
