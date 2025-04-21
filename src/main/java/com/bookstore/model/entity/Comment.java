package com.bookstore.model.entity;

import jakarta.persistence.*;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_comment")
  private int idComment;

  private String content;

  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(name = "id_book")
  @JsonIgnore
  private Book book;

  // Méthode pour définir le livre associé
  public void setBook(Book book) {
    if (this.book != null) {
      this.book.getComments().remove(this); // Retire ce commentaire de l'ancien livre
    }
    this.book = book;
    if (book != null) {
      book.getComments().add(this); // Ajoute ce commentaire au nouveau livre
    }
  }
}