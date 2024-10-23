package com.web.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.blog.model.CartItem;
import com.web.blog.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*") // Frontend'in çalıştığı port
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItem cartItem, @RequestHeader("Authorization") String token) {
        // Token doğrulaması yap
        // Eğer geçerli bir kullanıcıysa
        boolean success = cartService.addToCart(cartItem.getProductId(), token);
        if (success) {
            return ResponseEntity.ok("Product added to cart successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
    }
}