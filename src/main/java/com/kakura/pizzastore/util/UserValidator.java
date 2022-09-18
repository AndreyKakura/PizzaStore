package com.kakura.pizzastore.util;

import com.kakura.pizzastore.model.User;
import com.kakura.pizzastore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User userToBeChecked = (User) target;
        Optional<User> userFromDb = userService.findByEmail(userToBeChecked.getEmail());
        if (userFromDb.isPresent() && !Objects.equals(userFromDb.get().getId(), userToBeChecked.getId())) {
            errors.rejectValue("email", "", "Account with such email already exists");
        }
    }
}
