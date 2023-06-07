package com.example.common.models;

import com.example.common.interfaces.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class AbstractBaseEntity implements BaseEntity<String> {
    @Id
    @GeneratedValue
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setRandomId() {
        this.id = UUID.randomUUID().toString();
    }
}
