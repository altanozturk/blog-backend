package com.web.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.CartItem;
import com.web.blog.model.User;
import com.web.blog.repository.CartItemRepository;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService; // Kullanıcı bilgilerini almak için

    public boolean addToCart(String productId, String token) {
        // Token'dan kullanıcıyı al
        User user = userService.getUserFromToken(token);
        if (user == null) {
            return false; // Geçersiz kullanıcı
        }

        // Yeni sepet elemanı oluştur
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setUser(user); // Kullanıcıyı ata

        // Veritabanına kaydet
        cartItemRepository.save(cartItem);
        System.out.println("Product ID: " + productId + " added to cart for user: " + user.getUsername());
        return true; // Başarılı ekleme
    }
}
