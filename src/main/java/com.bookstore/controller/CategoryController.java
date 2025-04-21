package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.bookstore.model.entity.Category;
import com.bookstore.repository.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryRepository categoryRepository;

  @PostMapping
  @Transactional
  public ResponseEntity<Category> createCategory(@RequestBody Category category) {
    Category savedCategory = categoryRepository.save(category);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
  }

  @GetMapping
  public ResponseEntity<List<Category>> getCategories() {
    List<Category> categories = categoryRepository.findAll();
    return ResponseEntity.ok(categories);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable int id) {
    Optional<Category> category = categoryRepository.findById(id);
    if (!category.isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    categoryRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }
}