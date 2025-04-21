package com.bookstore.model.request;

import java.util.List;

import lombok.Data;

@Data
public class BookRequest {
  private String title;
  private String author;
  private double price;
  private String imageUrl;
  private List<Integer> categoryIds;
}