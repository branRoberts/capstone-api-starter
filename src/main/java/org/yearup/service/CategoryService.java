package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories from the database
        return categoryRepository.findAll();
    }

    public Category getById(int categoryId)
    {
        // find a single category by its id
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category create(Category category)
    {
        // save the new category and return it
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category)
    {
        // find the existing category, update its fields, save and return it
        Category existing = categoryRepository.findById(categoryId).orElseThrow();
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    public void delete(int categoryId)
    {
        // remove the category from the database by id
        categoryRepository.deleteById(categoryId);
    }
}
