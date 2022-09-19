package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.security.CustomUserDetails;
import com.kakura.pizzastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/add")
    public String addToCart(@RequestParam("id") Long id, @RequestParam("amount") long amount,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String user = userDetails.getUsername();
        cartService.addItem(id, amount, user);
        return "redirect:/pizza";
    }
}
