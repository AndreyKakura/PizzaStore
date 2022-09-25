package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.model.Role;
import com.kakura.pizzastore.model.User;
import com.kakura.pizzastore.security.CustomUserDetails;
import com.kakura.pizzastore.service.UserService;
import com.kakura.pizzastore.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public UserController(UserValidator userValidator, UserService userService) {
        this.userValidator = userValidator;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "users/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "users/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "users/registration";
        }

        userService.createUser(user);

        return "redirect:/users/login";
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute(userService.findOne(id));
        model.addAttribute("roles", List.of(Role.values()));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }
        userService.update(id, user);
        return "redirect:/users";
    }

    @PatchMapping("/{id}/changeRole")
    public String changeRole(@PathVariable("id") Long id, @RequestParam("role") Role role) {
        userService.changeRole(id, role);
        return "redirect:/users/{id}/edit";
    }

    @GetMapping("/editCurrent")
    public String editCurrent(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        model.addAttribute("user", userService.findOne(userDetails.getUser().getId()));
        return "users/editcurrent";
    }

    @PatchMapping("/editCurrent")
    public String editCurrent(@AuthenticationPrincipal CustomUserDetails userDetails,
                              @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        Long currentUserId = userDetails.getUser().getId();

        user.setId(currentUserId);

        userValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()) {
            return "users/editcurrent";
        }

        userService.update(userDetails.getUser().getId(), user);

        return "redirect:/users/profile";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("user", userService.findOne(userDetails.getUser().getId()));
        return "users/profile";
    }

    @PatchMapping("/{id}/changeActive")
    public String changeActive(@PathVariable("id") Long id) {
        userService.changeActive(id);
        return "redirect:/users/{id}/edit";
    }


}
