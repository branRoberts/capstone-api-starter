package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

// handles all requests coming in to /cart
// only logged in users can access any of these endpoints
@RestController
@RequestMapping("cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ShoppingCartController
{
    private ShoppingCartService shoppingCartService;
    private UserService userService;

    // Spring injects the services through the constructor
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService)
    {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }

    // returns the current user's cart with all items inside
    // principal gives us the logged in user's username
    @GetMapping
    public ShoppingCart getCart(Principal principal)
    {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        return shoppingCartService.getByUserId(userId);
    }

    // adds a product to the cart by product id
    // if the product is already in the cart, the quantity goes up by 1
    @PostMapping("products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCart addProduct(@PathVariable int productId, Principal principal)
    {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        return shoppingCartService.addProduct(userId, productId);
    }

    // updates the quantity of a product already in the cart
    @PutMapping("products/{productId}")
    public ShoppingCart updateProduct(@PathVariable int productId, @RequestBody ShoppingCartItem item, Principal principal)
    {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        return shoppingCartService.updateProduct(userId, productId, item.getQuantity());
    }

    // removes all items from the current user's cart
    // returns the now empty cart so the frontend can refresh
    @DeleteMapping
    public ShoppingCart clearCart(Principal principal)
    {
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();
        return shoppingCartService.clearCart(userId);
    }
}