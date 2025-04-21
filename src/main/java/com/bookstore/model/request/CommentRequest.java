package com.bookstore.model.request;

import lombok.Data;

@Data
public class CommentRequest {
  private String content;
  private Integer idBook;
}
