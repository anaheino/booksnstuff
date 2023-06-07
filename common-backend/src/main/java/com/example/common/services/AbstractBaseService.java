package com.example.common.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseService<T, ID> {
    private final JpaRepository<T, ID> repository;

    public AbstractBaseService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> list() {
        return repository.findAll();
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public T update(ID entityId, T entity) {
        Optional<T> previousEntity = repository.findById(entityId);
        if (previousEntity.isPresent() && previousEntity.get().equals(entity)) {
            return entity;
        }
        return repository.save(entity);
    }
}
