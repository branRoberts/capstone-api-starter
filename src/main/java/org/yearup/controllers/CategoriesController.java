package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;

// handles all requests coming in to /categories
@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;

    // Spring injects the service dependencies through the constructor
    public CategoriesController(CategoryService categoryService, ProductService productService)
    {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // returns all categories - available to anyone
    @GetMapping
    public List<Category> getAll()
    {
        return categoryService.getAllCategories();
    }

    // returns a single category by id - returns 404 if the id doesn't exist
    @GetMapping("{id}")
    public ResponseEntity<Category> getById(@PathVariable int id)
    {
        Category category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    // returns all products that belong to a specific category
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        return productService.listByCategoryId(categoryId);
    }

    // adds a new category - only admins are allowed to do this
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        Category created = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // updates an existing category by id - only admins are allowed to do this
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        return categoryService.update(id, category);
    }

    // deletes a category by id - only admins are allowed to do this
    // returns 204 No Content on success
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}