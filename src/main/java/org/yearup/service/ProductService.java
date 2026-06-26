package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    // searches for products based on optional filters - category, price range, and color
    // if no category is given, it loads all products first then filters down
    public List<Product> search(Integer categoryId, Double minPrice, Double maxPrice, String subCategory)
    {
        List<Product> products = categoryId != null
                ? productRepository.findByCategoryId(categoryId)
                : productRepository.findAll();

        return products.stream()
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .filter(p -> subCategory == null || subCategory.equalsIgnoreCase(p.getSubCategory()))
                .toList();
        // removed isFeatured filter - it was blocking valid search results
    }

    // returns all products that belong to a specific category
    public List<Product> listByCategoryId(int categoryId)
    {
        return productRepository.findByCategoryId(categoryId);
    }

    // finds a single product by its id, returns null if not found
    public Product getById(int productId)
    {
        return productRepository.findById(productId).orElse(null);
    }

    // saves a new product to the database
    // productId is set to 0 so the database generates a new id
    public Product create(Product product)
    {
        product.setProductId(0);
        return productRepository.save(product);
    }

    // updates an existing product with new values
    // finds the product first, then updates each field and saves it
    public Product update(int productId, Product product)
    {
        Product existing = productRepository.findById(productId).orElseThrow();
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategoryId(product.getCategoryId());
        existing.setDescription(product.getDescription());
        existing.setSubCategory(product.getSubCategory());
        existing.setFeatured(product.isFeatured());
        existing.setImageUrl(product.getImageUrl());
        existing.setStock(product.getStock());
        // added missing stock update - stock changes were not being saved
        return productRepository.save(existing);
    }

    // removes a product from the database by its id
    public void delete(int productId)
    {
        productRepository.deleteById(productId);
    }
}