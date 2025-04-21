package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookstore.model.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

  public Iterable<Book> findByTitle(String title);

  public Iterable<Book> findByCategoriesName(String name);

  @Query("FROM Book WHERE title = ?1")
  public Iterable<Book> findByTitleJPQL(String name);
}
