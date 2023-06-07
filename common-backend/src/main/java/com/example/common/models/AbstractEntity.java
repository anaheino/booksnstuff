package com.example.common.models;

import com.example.common.interfaces.Entity;

import java.util.UUID;

public abstract class AbstractEntity implements Entity<String> {
    public String id;
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
