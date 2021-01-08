package br.com.nfe.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;
import java.util.List;

public interface CrudController<T, ID extends Serializable> {

    @RequestMapping(value = {"{id}"}, method = {RequestMethod.GET})
    T findOne(@PathVariable("id") ID id);

    @RequestMapping(method = {RequestMethod.POST})
    T persist(@RequestBody T entity);

    @RequestMapping(value = {"list"}, method = {RequestMethod.POST})
    List<T> persist(@RequestBody List<T> entity);

    @RequestMapping(value = {"{id}"}, method = {RequestMethod.DELETE})
    void delete(@PathVariable("id") ID id);
}
