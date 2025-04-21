package com.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.model.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

  List<Comment> findByBookIdBook(int bookIdBook);
}
