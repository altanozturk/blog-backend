package com.web.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.blog.model.CartItem;
import com.web.blog.model.User;
import com.web.blog.service.CartService;
import com.web.blog.service.UserService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // Frontend'in çalıştığı port
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String token) {
        // Token doğrulaması yap
        // Eğer geçerli bir kullanıcıysa
        boolean success = cartService.addToCart(cartItem.getImgSrc(), cartItem.getPrice(), cartItem.getTitle(), cartItem.getProductId(), token);
        if (success) {
            return ResponseEntity.ok("Product added to cart successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("Authorization") String token) {
        User user = userService.getUserFromToken(token);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Kullanıcı doğrulanmadıysa yetkisiz
        }

        List<CartItem> cartItems = cartService.getCartItemsByUser(user);
        return ResponseEntity.ok(cartItems); // Kullanıcıya ait sepet öğelerini döndür
    }
}