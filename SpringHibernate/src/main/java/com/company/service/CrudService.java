package com.company.service;

import java.util.List;
import java.util.Optional;

public interface CrudService {

    Optional findOne(long id);

    List findAll();

    boolean saveOrUpdate(Object o);

    boolean delete(Object o);
}
