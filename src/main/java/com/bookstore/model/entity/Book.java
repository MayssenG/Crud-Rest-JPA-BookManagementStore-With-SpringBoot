package com.bookstore.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_book")
  private int idBook;

  private String title;
  private String author;
  private double price;

  @Column(name = "image_url")
  private String imageUrl;

  @ManyToMany(
      fetch = FetchType.LAZY
  )
  @JoinTable(
      name = "category_book",
      joinColumns = @JoinColumn(name = "id_book"),
      inverseJoinColumns = @JoinColumn(name = "id_category")
  )
  private List<Category> categories = new ArrayList<>();

  @OneToMany(
      mappedBy = "book",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  private List<Comment> comments = new ArrayList<>();

  public void addCategory(Category category) {
    categories.add(category);
    category.getBooks().add(this);
  }

  public void removeCategory(Category category) {
    categories.remove(category);
    category.getBooks().remove(this);
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setBook(this);
  }

  public void removeComment(Comment comment) {
    comments.remove(comment);
    comment.setBook(null);
  }
}