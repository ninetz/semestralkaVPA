package com.semestralka.semestralkaVPA.services;

import com.semestralka.semestralkaVPA.entities.User;
import com.semestralka.semestralkaVPA.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    public boolean registerUser(String name, String password) {
        if (userRepository.findByUsername(name) == null) {
            User user = new User();
            String salt = BCrypt.gensalt();
            String hashedPassword = BCrypt.hashpw(password, salt);
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setUsername(name);
            userRepository.save(user);
            return true;

        }
        return false;
    }
}
