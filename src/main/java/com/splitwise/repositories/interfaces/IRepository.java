package com.splitwise.repositories.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IRepository<E, Id> {
    public void create(E entity);

    public Optional<E> findById(Id id);

    public ArrayList<E> findAll();

    public void save(E user);

    public void delete(Id id);

}
