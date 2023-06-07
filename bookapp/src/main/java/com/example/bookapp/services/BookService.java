package com.example.bookapp.services;

import com.example.bookapp.models.Book;
import com.example.bookapp.repositories.BookRepository;
import com.example.common.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService extends AbstractBaseService<Book, String> {

    @Autowired
    public BookService(BookRepository bookRepository) {
        super(bookRepository);
    }
}
