package com.example.common.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    public String getId() {
        return id;
    }
    public void setRandomId() {
        this.id = UUID.randomUUID().toString();
    }
    public void setId(String id)  {
        this.id = id;
    }
}
