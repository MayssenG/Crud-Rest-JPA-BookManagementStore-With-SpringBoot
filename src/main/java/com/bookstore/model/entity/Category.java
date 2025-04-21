package com.bookstore.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_category")
  private int idCategory;

  private String name;

  @ManyToMany(
      mappedBy = "categories",
      fetch = FetchType.LAZY
  )
  @JsonIgnore
  private List<Book> books = new ArrayList<>();

  @PreRemove
  private void removeBooks() {
    for (Book book : books) {
      book.getCategories().remove(this);
    }
  }

  public void addBook(Book book) {
    books.add(book);
    book.getCategories().add(this);
  }

  public void removeBook(Book book) {
    books.remove(book);
    book.getCategories().remove(this);
  }
}