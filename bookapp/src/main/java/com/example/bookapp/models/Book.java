package com.example.bookapp.models;

import com.example.common.models.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(name = "book_year_idx", columnList = "year")
})
public class Book extends BaseEntity {
    private String title;
    private String author;
    private int year;
    private BigDecimal price;
}
