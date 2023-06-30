package com.example.bookapp.controllers;

import com.example.bookapp.models.Book;
import com.example.bookapp.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.common.web.BookAppUrlSchema.BOOK;
import static com.example.common.web.BookAppUrlSchema.BOOKS;

@RestController
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = BOOKS)
    public List<Book> getBooks() {
        return bookService.list();
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = BOOK)
    public Book getBook(@PathVariable String id) {
        return bookService.findById(id).orElse(null);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value = BOOK)
    public Book updateBook(@PathVariable String id, Book book) {
        Book previousBook = bookService.findById(id).orElse(null);
        // Again, this is not at all how this would happen in actual production, but in POC, who cares.
        if (previousBook != null) {
            previousBook.setAuthor(book.getAuthor());
            previousBook.setTitle(book.getTitle());
            previousBook.setPrice(book.getPrice());
            previousBook.setYear(book.getYear());
            return bookService.update(id, previousBook);
        }
        return bookService.update(id, book);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(value = BOOK)
    public void delete(@PathVariable String id) {
        bookService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = BOOK)
    public void create(Book book) {
        book.setRandomId();
        bookService.update(book.getId(), book);
    }
}
