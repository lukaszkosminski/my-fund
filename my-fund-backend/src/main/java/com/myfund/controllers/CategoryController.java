package com.myfund.controllers;

import com.myfund.models.DTOs.CategoryDTO;
import com.myfund.models.DTOs.CreateCategoryDTO;
import com.myfund.models.User;
import com.myfund.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(categoryService.findAllCategoriesByUser(user), HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal User user) {
        Optional<CategoryDTO> categoryOpt = categoryService.findCategoryByIdAndUser(categoryId, user);
        if (categoryOpt.isPresent()) {
            return new ResponseEntity<>(categoryOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CreateCategoryDTO createCategoryDTO, @AuthenticationPrincipal User user) {
        Optional<CategoryDTO> category = categoryService.createCategory(createCategoryDTO, user);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CreateCategoryDTO createCategoryDTO, @AuthenticationPrincipal User user) {
        Optional<CategoryDTO> category = categoryService.updateCategory(categoryId, createCategoryDTO, user);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal User user) {
        try {
            categoryService.deleteCategoryByIdAndUser(categoryId, user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}