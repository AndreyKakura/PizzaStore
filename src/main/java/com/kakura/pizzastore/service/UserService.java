package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.User;
import com.kakura.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean createUser(User user) {

        user.setActive(true);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole("ROLE_USER");

        userRepository.save(user);

        return true;
    }
}
