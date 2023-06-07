package com.example.common.interfaces;

public interface Entity<ID> {
    ID getId();
    void setId(ID id);
    void setRandomId();
}
