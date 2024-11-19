package com.web.blog.service;

import com.web.blog.model.User;
import com.web.blog.repository.UserRepository;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder crypt;

    private String secretKey = "secret_key";
    private long expirationTime = 86400000; // Tokenin geçerli olma süresi (1 gün)

    public void registerUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(crypt.encode(password));
        System.out.println("görünüyor mu?");
        userRepository.save(user);
    }

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && crypt.matches(password, user.getPassword())) {
            return generateToken(user.getUsername()); // Başarıyla giriş yaptıysa token döndür
        }
        return null; // Hatalı giriş
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserFromToken(String token) {
        try {
            // Token içindeki son "-" karakterinin konumunu bul
            int lastDashIndex = token.lastIndexOf("-");
            
            // Eğer "-" bulunamazsa ya da username kısmı yoksa null döndür
            if (lastDashIndex == -1 || lastDashIndex == token.length() - 1) {
                return null;
            }
            
            // Username kısmını son "-" karakterinden itibaren al
            String username = token.substring(lastDashIndex + 1);
            
            // Veritabanından kullanıcıyı bul ve döndür
            return userRepository.findByUsername(username);
            
        } catch (Exception e) {
            return null; // Geçersiz token durumunda null döndür
        }
    }

    public String generateToken(String username) {

        String token = UUID.randomUUID().toString() + "-" + username;
        return token;

    }

}