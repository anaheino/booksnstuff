package com.example.bookapp.controller;

import com.example.bookapp.BookApp;
import com.example.bookapp.models.Book;
import com.example.bookapp.repositories.BookRepository;
import com.example.common.models.user.Role;
import com.example.common.models.user.User;
import com.example.common.repositories.UserRepository;
import com.example.common.services.JwtService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.common.web.BookAppUrlSchema.BOOK;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ContextConfiguration(classes = BookApp.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTests {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private WebTestClient client;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @LocalServerPort
    private Integer port;

    private User user;
    private Book book;
    private String token;

    @BeforeAll
    void init() {
        book = Book.builder()
                .author("Author")
                .price(new BigDecimal(10))
                .title("title")
                .year(1990)
                .build();
        book.setRandomId();
        book = bookRepository.save(book);
        user = User.builder()
                .email("someEmail@gmail.com")
                .password(passwordEncoder.encode("a"))
                .firstName("name")
                .lastName("name")
                .role(Role.USER)
                .build();
        user.setRandomId();
        user = userRepository.save(user);
        user.setPassword("a");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        token = "JWT_TOKEN=" + jwtService.generateToken(user);
    }

    @AfterAll
    void cleanTestData() {
        bookRepository.deleteById(book.getId());
        userRepository.deleteById(user.getId());
    }

    @Test
    void getBook() throws URISyntaxException {
        final String id = book.getId();
        client.get()
                .uri(new URI("http://localhost:" + port + BOOK.replace("{id}", book.getId())))
                .header("cookie",  token )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .consumeWith(bookEntityExchangeResult -> {
                    assertThat(Objects.requireNonNull(bookEntityExchangeResult.getResponseBody()).getId()).isEqualTo(id);
                });
    }

    @Test
    void putBook() throws URISyntaxException {
        Book updatedBook = Book.builder()
                .author(book.getAuthor())
                .price(book.getPrice())
                .title("updatedTitle")
                .year(book.getYear())
                .build();
        updatedBook.setId(book.getId());
        client.put()
                .uri(new URI("http://localhost:" + port + BOOK.replace("{id}", book.getId())))
                .bodyValue(updatedBook)
                .header("cookie",  token )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .consumeWith(bookEntityExchangeResult -> {
                    assertThat(Objects.requireNonNull(bookEntityExchangeResult.getResponseBody()).getTitle()).isEqualTo("updatedTitle");
                });
        Optional<Book> result = bookRepository.findById(book.getId());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTitle()).isEqualTo("updatedTitle");
    }

    @Test
    void createAndDeleteBook() throws URISyntaxException {
        Book newBook = Book.builder()
                .author(book.getAuthor())
                .price(book.getPrice())
                .title("someTitle")
                .year(book.getYear())
                .build();
        newBook.setRandomId();
        AtomicReference<String> id = new AtomicReference<>();
        client.post()
                .uri(new URI("http://localhost:" + port + BOOK.replace("{id}", newBook.getId())))
                .bodyValue(newBook)
                .header("cookie",  token )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class)
                .consumeWith(bookEntityExchangeResult -> {
                    Book responseBook = Objects.requireNonNull(bookEntityExchangeResult.getResponseBody());
                    id.set(responseBook.getId());
                    assertThat(responseBook.getTitle()).isEqualTo("someTitle");
                });
        Optional<Book> result = bookRepository.findById(id.get());
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTitle()).isEqualTo("someTitle");
        client.delete()
                .uri(new URI("http://localhost:" + port + BOOK.replace("{id}", id.get())))
                .header("cookie",  token )
                .exchange()
                .expectStatus()
                .isOk();
        result = bookRepository.findById(id.get());
        assertThat(result.isEmpty()).isTrue();
    }
}
