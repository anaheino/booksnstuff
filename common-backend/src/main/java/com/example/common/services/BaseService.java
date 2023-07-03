package com.example.common.services;

import com.example.common.models.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, ID> {
    public final JpaRepository<T, ID> repository;

    public BaseService(JpaRepository<T, ID> repository) {
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

    // Entity id is here in case we want to code a difference check between old and updated entity
    public T update(ID entityId, T entity) {
        entity.setId((String) entityId);
        return repository.save(entity);
    }
}
