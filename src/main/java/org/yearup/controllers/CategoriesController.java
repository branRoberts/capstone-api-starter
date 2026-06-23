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

@RestController
@RequestMapping("categories")
@CrossOrigin
public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;

    public CategoriesController(CategoryService categoryService, ProductService productService)
    {
        this.categoryService = categoryService;
        this.productService = productService;
    }
    // return all categories from the database
    @GetMapping
    public List<Category> getAll()
    {
        return categoryService.getAllCategories();
    }
    // find a single category by its id, return 404 if not found
    @GetMapping("{id}")
    public ResponseEntity<Category> getById(@PathVariable int id)
    {
        Category category = categoryService.getById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        return productService.listByCategoryId(categoryId);
    }
    // save a new category to the database and return it
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        Category created = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    // find the existing category, update its fields, and save it
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        return categoryService.update(id, category);
    }
    // remove the category from the database by id
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}