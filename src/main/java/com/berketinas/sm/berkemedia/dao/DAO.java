package com.berketinas.sm.berkemedia.dao;

import java.util.List;
import java.util.UUID;

// ***
// basic DAO layer for CRUD operations
// ***
public interface DAO<T> {
    T find(UUID id);
    List<T> findAll();
    void save(T t);
    void delete(UUID id);
}
