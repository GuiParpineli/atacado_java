package com.wf.apiwf.service;

import com.wf.apiwf.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public  interface IService<T>{

    public ResponseEntity getAll();

    public ResponseEntity<?> get(Long id) throws ResourceNotFoundException;

    public ResponseEntity<?> save(T t) throws ResourceNotFoundException;

    public void delete(Long id);

    public void update(T t);

}
