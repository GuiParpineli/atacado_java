package com.wf.apiwf.service.impl;

import com.wf.apiwf.entity.SystemUser;
import com.wf.apiwf.repository.IUserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final IUserRepository repository;
    final static Logger log = Logger.getLogger(UserService.class);

    @Autowired
    public UserService(IUserRepository repository) {this.repository = repository;}

    public List<SystemUser> getAll() {
        return repository.findAll();
    }

    public Optional<SystemUser> get(Long id) {
        return repository.findById(id);
    }

    public SystemUser save(SystemUser systemUser) {
        log.info("Usuario salvo com sucesso");
        return repository.save(systemUser);
    }

    public void delete(Long id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            log.info("Usuario deletado com sucesso");
        }
    }

    public void update(SystemUser systemUser) {
        if (systemUser != null & repository.findById(systemUser.getId()).isPresent()) {
            repository.saveAndFlush(systemUser);
            log.info("Usuario atualizado com sucesso");
        }
    }

    public SystemUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario nao " +
                "encontrado"));
    }

    public SystemUser findByName(String name) {
        return repository.findByName(name);
    }

    public  SystemUser findByEmail(String email){
        return repository.findByEmail(email);
    }
}
