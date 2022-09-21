package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.model.Image;
import com.kakura.pizzastore.model.Pizza;
import com.kakura.pizzastore.security.CustomUserDetails;
import com.kakura.pizzastore.service.CartService;
import com.kakura.pizzastore.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    private final PizzaService pizzaService;
    private final CartService cartService;

    @Autowired
    public PizzaController(PizzaService pizzaService, CartService cartService) {
        this.pizzaService = pizzaService;
        this.cartService = cartService;
    }

    @GetMapping()
    public String index(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if( userDetails != null) {
            model.addAttribute("mapOfIdAndAmount", cartService.getMapOfPizzaIdAndAmountForUser(userDetails.getUser().getId()));
        }
        model.addAttribute("pizzas", pizzaService.findAll());
        return "pizza/index";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("pizza") Pizza pizza) {
        return "pizza/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("pizza") @Valid Pizza pizza, BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile imageFile) {
        if (bindingResult.hasErrors()) {
            return "pizza/new";
        }
        pizzaService.save(pizza, imageFile);
        return "redirect:/pizza";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("pizza", pizzaService.findOne(id));
        return "pizza/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("pizza") @Valid Pizza pizza, BindingResult bindingResult, @RequestParam("imageFile") MultipartFile imageFile, @PathVariable("id") Long id) {
        if(bindingResult.hasErrors()) {
            return "pizza/edit";
        }

        pizzaService.update(id, pizza, imageFile);

        return "redirect:/pizza";
    }
}
