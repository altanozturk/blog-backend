package com.web.blog.service;

import com.web.blog.model.User;
import com.web.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder crypt;

    private String secretKey = "secret_key";
    

    public void registerUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(crypt.encode(password));
        System.out.println("görünüyor mu?");
        userRepository.save(user);
    }

    public boolean loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && crypt.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserFromToken(String token) {
        try {
            // Token'ı doğrula ve "claims" kısmını al
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey) // Gizli anahtar ile token'ı doğrula
                    .parseClaimsJws(token.replace("Bearer ", "")) // "Bearer " kısmını temizle
                    .getBody();

            // Claims içerisinden kullanıcı adını al
            String username = claims.getSubject();

            // Veritabanından kullanıcıyı al
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            return null; // Geçersiz token durumunda null döndür
        }
    }
}