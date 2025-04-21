package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.Category;
import com.bookstore.model.request.BookRequest;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;

@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<Book> createBook(@RequestBody BookRequest bookRequest) {
    Book book = new Book();

    book.setTitle(bookRequest.getTitle());
    book.setAuthor(bookRequest.getAuthor());
    book.setPrice(bookRequest.getPrice());
    book.setImageUrl(bookRequest.getImageUrl());

    List<Category> categories = categoryRepository.findAllById(bookRequest.getCategoryIds());
    book.setCategories(categories);

    Book savedBook = bookRepository.save(book);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
  }

  @GetMapping
  public ResponseEntity<List<Book>> getBooks() {
    List<Book> books = bookRepository.findAll();
    return ResponseEntity.ok(books);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Book> getBook(@PathVariable int id) {
    Optional<Book> book = bookRepository.findById(id);
    return book.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookRequest bookRequest) {
    Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

    existingBook.setTitle(bookRequest.getTitle());
    existingBook.setAuthor(bookRequest.getAuthor());
    existingBook.setPrice(bookRequest.getPrice());
    existingBook.setImageUrl(bookRequest.getImageUrl());

    List<Category> categories = categoryRepository.findAllById(bookRequest.getCategoryIds());

    if (categories.size() != bookRequest.getCategoryIds().size()) {
      throw new RuntimeException("One or more categories not found");
    }

    existingBook.setCategories(categories);

    Book updatedBook = bookRepository.save(existingBook);

    return ResponseEntity.ok(updatedBook);
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<?> deleteBook(@PathVariable int id) {
    if (!bookRepository.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    bookRepository.deleteById(id);

    return ResponseEntity.ok().build();
  }
}