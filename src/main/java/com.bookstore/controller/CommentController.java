package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.entity.Book;
import com.bookstore.model.entity.Comment;
import com.bookstore.model.request.CommentRequest;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CommentRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private BookRepository bookRepository;

  @PostMapping
  public ResponseEntity<Comment> createComment(@RequestBody CommentRequest commentRequest) {
    Comment comment = new Comment();

    comment.setContent(commentRequest.getContent());
    Optional<Book> book = bookRepository.findById(commentRequest.getIdBook());

    book.ifPresent(comment::setBook);
    Comment savedComment = commentRepository.save(comment);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
  }

  @Transactional(readOnly = true)
  @GetMapping
  public ResponseEntity<List<Comment>> getComments() {
    List<Comment> comments = commentRepository.findAll();
    return ResponseEntity.ok(comments);
  }

  @Transactional(readOnly = true)
  @GetMapping("/book/{bookIdBook}")
  public ResponseEntity<List<Comment>> getBookComments(@PathVariable int bookIdBook) {
    List<Comment> comments = commentRepository.findByBookIdBook(bookIdBook);

    return ResponseEntity.ok(comments);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteComment(@PathVariable int id) {
    Optional<Comment> comment = commentRepository.findById(id);
    if (!comment.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    commentRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}