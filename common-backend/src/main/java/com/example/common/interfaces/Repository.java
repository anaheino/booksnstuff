package com.example.common.interfaces;

import jakarta.annotation.Nullable;

public interface Repository<T, ID> {
    @Nullable
    T loadDocument(ID id);
}
