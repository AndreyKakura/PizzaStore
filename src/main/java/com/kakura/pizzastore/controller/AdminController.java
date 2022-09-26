package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.model.Role;
import com.kakura.pizzastore.model.User;
import com.kakura.pizzastore.service.UserService;
import com.kakura.pizzastore.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id) {
        model.addAttribute(userService.findOne(id));
        model.addAttribute("roles", List.of(Role.values()));
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") Long id) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/edit";
        }
        userService.update(id, user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/changeRole")
    public String changeRole(@PathVariable("id") Long id, @RequestParam("role") Role role) {
        userService.changeRole(id, role);
        return "redirect:/admin/{id}/edit";
    }

    @PatchMapping("/{id}/changeActive")
    public String changeActive(@PathVariable("id") Long id) {
        userService.changeActive(id);
        return "redirect:/admin/{id}/edit";
    }
}
