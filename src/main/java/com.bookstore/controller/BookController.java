package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@RestController
public class BookController {

  @Autowired
  BookRepository bookRepository;

  @PostMapping("/books")
  public Book createBook(@RequestBody Book book) {
    Book savedBook = bookRepository.save(book);

    return savedBook;
  }

  @GetMapping("/books")
  public List<Book> getBooks() {
    List<Book> books = bookRepository.findAll();

    return books;
  }

  @PutMapping("/books/{id}")
  public Book updateBook(@PathVariable int id, @RequestBody Book book) {
    book.setId(id);

    return bookRepository.save(book);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<?> deleteBook(@PathVariable int id) {
    bookRepository.deleteById(id);

    return ResponseEntity.ok().build();
  }
}
