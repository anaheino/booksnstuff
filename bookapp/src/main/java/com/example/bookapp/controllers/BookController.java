package com.example.bookapp.controllers;

import com.example.bookapp.models.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.common.web.BookAppUrlSchema.BOOKS;

@RestController
public class BookController {

    @GetMapping(value = BOOKS)
    public Book getBooks() {
        return new Book();
    }
}
