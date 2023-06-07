package com.example.bookapp.controllers;

import com.example.bookapp.models.Book;
import com.example.bookapp.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.common.web.BookAppUrlSchema.BOOK;
import static com.example.common.web.BookAppUrlSchema.BOOKS;

@RestController
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = BOOKS)
    public List<Book> getBooks() {
        return bookService.list();
    }

    @GetMapping(value = BOOK)
    public Book getBook(@PathVariable String id) {
        return bookService.findById(id).orElse(null);
    }

    @PutMapping(value = BOOK)
    public Book updateBook(@PathVariable String id, @RequestBody Book book) {
        return bookService.update(id, book);
    }

    @DeleteMapping(value = BOOK)
    public void delete(@PathVariable String id) {
        bookService.deleteById(id);
    }

    @PostMapping(value = BOOK)
    public void create(@RequestBody Book book) {
        book.setRandomId();
        bookService.update(book.getId(), book);
    }
}
