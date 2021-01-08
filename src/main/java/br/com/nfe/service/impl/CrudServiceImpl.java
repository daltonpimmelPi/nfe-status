package br.com.nfe.service.impl;

import br.com.nfe.service.CrudService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public abstract class CrudServiceImpl<T, ID extends Serializable> implements CrudService<T, ID> {

    protected abstract CrudRepository<T, ID> repository();

    @Override
    public T save(T entity) {
        return repository().save(entity);
    }

    @Override
    public T findOne(ID id) {
        return repository().findById(id).orElse(null);
    }

    @Override
    public void delete(ID id) {
        repository().deleteById(id);
    }

    @Override
    public Iterable<T> save(Iterable<T> iterable) {
        return repository().saveAll(iterable);
    }


}
