package com.web.blog.service;

import java.util.List;

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

    public boolean addToCart(String imgSrc, String price, String title, String productId, String token) {
        // Token'dan kullanıcıyı al
        User user = userService.getUserFromToken(token);
        if (user == null) {
            return false; // Geçersiz kullanıcı
        }

        // Yeni sepet elemanı oluştur
        CartItem cartItem = new CartItem();
        cartItem.setProductId(productId);
        cartItem.setImgSrc(imgSrc);
        cartItem.setPrice(price);
        cartItem.setTitle(title);
        cartItem.setUser(user); // Kullanıcıyı ata

        // Veritabanına kaydet
        cartItemRepository.save(cartItem);
        System.out.println(title + " added to cart for user: " + user.getUsername());
        return true; // Başarılı ekleme
    }

    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findByUser(user); // Kullanıcının tüm sepet öğelerini döndür
    }
}
