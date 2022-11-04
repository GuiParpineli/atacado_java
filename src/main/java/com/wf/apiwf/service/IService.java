package com.wf.apiwf.service;

import java.util.List;
import java.util.Optional;

public  interface IService<T>{

    public List<T> getAll();

    public Optional<T> get(Long id);

    public  T save(T t);

    public void delete(Long id);

    public void update(T t);

}
