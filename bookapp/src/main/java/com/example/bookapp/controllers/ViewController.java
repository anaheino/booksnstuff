package com.example.bookapp.controllers;

import com.example.bookapp.models.Book;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.net.URI;
import java.net.URISyntaxException;

import static com.example.common.web.BookAppUrlSchema.BOOK_PAGE;
import static com.example.common.web.BookAppUrlSchema.BOOK_STUPID;
import static com.example.common.web.BookAppUrlSchema.BOOK_STUPID_ID;

@Controller
@AllArgsConstructor
public class ViewController {

    private final BookController bookController;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(BOOK_PAGE)
    public String booksPage(Model model) {
        model.addAttribute("books", bookController.getBooks());
        return "books";
    }


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(BOOK_STUPID)
    public ResponseEntity<String> createBook(@ModelAttribute("newBook") Book book) throws URISyntaxException {
        book.setRandomId();
        bookController.createThymeleaf(book.getId(), book);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(new URI("http://localhost:8080/v1/api/book-page"))
                .build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(BOOK_STUPID_ID)
    public ResponseEntity<String> updateBook(@PathVariable String id, @ModelAttribute("formData") Book book) throws URISyntaxException {
        bookController.updateBookThymeleaf(id, book);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(new URI("http://localhost:8080/v1/api/book-page"))
                .build();
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping(BOOK_STUPID_ID)
    public ResponseEntity<String> deleteBook(@PathVariable String id) throws URISyntaxException {
        bookController.delete(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(new URI("http://localhost:8080/v1/api/book-page"))
                .build();
    }
}
