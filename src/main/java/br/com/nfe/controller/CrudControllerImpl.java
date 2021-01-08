package br.com.nfe.controller;

import br.com.nfe.service.CrudService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.List;

@Component
public abstract class CrudControllerImpl<T, ID extends Serializable> implements CrudController<T, ID>{

    public abstract CrudService<T, ID> service();

    @ApiIgnore
    public T persist(@RequestBody T entity) {
        return this.service().save(entity);
    }

    @ApiIgnore
    public List<T> persist(@RequestBody List<T> entity) {
        return (List<T>) this.service().save(entity);
    }

    public T findOne(@PathVariable ID id) {
        return this.service().findOne(id);
    }

    @ApiIgnore
    public void delete(@PathVariable ID id) {
        this.service().delete(id);
    }

}
