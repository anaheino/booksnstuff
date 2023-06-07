package com.example.common.interfaces;

public interface BaseEntity<ID> {
    ID getId();
    void setId(ID id);
    void setRandomId();
}
