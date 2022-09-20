package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.security.CustomUserDetails;
import com.kakura.pizzastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("items", cartService.findOneByUserEmail(userDetails.getUsername()));
        return "cart/index";
    }

    @PutMapping("/add")
    public String addToCart(@RequestParam("id") Long id,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String user = userDetails.getUsername();
        cartService.addToCart(id, user);
        return "redirect:/pizza";
    }

}
