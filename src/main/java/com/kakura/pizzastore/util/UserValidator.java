package com.kakura.pizzastore.util;

import com.kakura.pizzastore.model.User;
import com.kakura.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;
        if (userRepository.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("username", "", "Account with such email already exists");
        }
    }
}
