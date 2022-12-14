package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.Cart;
import com.kakura.pizzastore.model.Role;
import com.kakura.pizzastore.model.User;
import com.kakura.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createUser(User user) {
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setCart(new Cart());
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User updatedUser) {
        User userToBeUpdated = userRepository.findById(id).get();
        updatedUser.setId(id);
        updatedUser.setActive(userToBeUpdated.isActive());
        updatedUser.setRole(userToBeUpdated.getRole());
        updatedUser.setPassword(userToBeUpdated.getPassword());
        return userRepository.save(updatedUser);
    }

    @Transactional
    public User changeRole(Long id, Role role) {
        User user = userRepository.findById(id).get();
        user.setRole(role);
        return userRepository.save(user);
    }

    @Transactional
    public User changeActive(Long id) {
        User user = userRepository.findById(id).get();
        user.setActive(!user.isActive());
        return userRepository.save(user);
    }
}
