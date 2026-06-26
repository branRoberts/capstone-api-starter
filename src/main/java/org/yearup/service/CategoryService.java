package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

// handles the business logic for category operations
// sits between the controller and the repository
@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    // Spring injects the repository through the constructor
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    // gets every category from the database and returns them as a list
    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    // looks up one category by its id
    // returns null if no category with that id exists
    public Category getById(int categoryId)
    {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    // saves a new category to the database and returns the saved version
    public Category create(Category category)
    {
        return categoryRepository.save(category);
    }

    // finds the existing category, updates its name and description, then saves it
    public Category update(int categoryId, Category category)
    {
        Category existing = categoryRepository.findById(categoryId).orElseThrow();
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    // removes a category from the database by its id
    public void delete(int categoryId)
    {
        categoryRepository.deleteById(categoryId);
    }
}