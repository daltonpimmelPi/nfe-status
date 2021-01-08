package br.com.nfe.service;

import java.io.Serializable;

public interface CrudService<T, ID extends Serializable> {

    T save(T entity);

    T findOne(ID id);

    void delete(ID id);

    Iterable<T> save(Iterable<T> iterable);

}
