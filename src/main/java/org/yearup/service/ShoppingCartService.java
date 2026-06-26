package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// handles the business logic for shopping cart operations
// sits between the controller and the repository
@Service
public class ShoppingCartService
{
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    // Spring injects both dependencies through the constructor
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    // loads all cart rows for the user, looks up each product,
    // and builds a ShoppingCart object to return
    public ShoppingCart getByUserId(int userId)
    {
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        ShoppingCart cart = new ShoppingCart();

        for (CartItem cartItem : cartItems)
        {
            Product product = productService.getById(cartItem.getProductId());
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            cart.add(item);
        }

        return cart;
    }

    // adds a product to the cart
    // if the product is already there, it increases the quantity by 1 instead of adding a duplicate
    public ShoppingCart addProduct(int userId, int productId)
    {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing == null)
        {
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            shoppingCartRepository.save(newItem);
        }
        else
        {
            existing.setQuantity(existing.getQuantity() + 1);
            shoppingCartRepository.save(existing);
        }

        return getByUserId(userId);
    }

    // updates the quantity of a specific product already in the cart
    // only updates if the product is actually in the cart
    public ShoppingCart updateProduct(int userId, int productId, int quantity)
    {
        CartItem existing = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (existing != null)
        {
            existing.setQuantity(quantity);
            shoppingCartRepository.save(existing);
        }

        return getByUserId(userId);
    }

    // deletes all items from the user's cart
    // @Transactional is required here because deleteByUserId is a custom delete query
    @Transactional
    public ShoppingCart clearCart(int userId)
    {
        shoppingCartRepository.deleteByUserId(userId);
        return getByUserId(userId);
    }
}