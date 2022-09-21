package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.model.Cart;
import com.kakura.pizzastore.security.CustomUserDetails;
import com.kakura.pizzastore.service.CartItemService;
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

    private final CartItemService cartItemService;

    @Autowired
    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        Cart cart = cartService.findOneByUserEmail(userDetails.getUsername());
        model.addAttribute("items", cart.getCartItems());
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(cart));
        return "cart/index";
    }

    @PutMapping("/add")
    public String addToCart(@RequestParam("id") Long id,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        String user = userDetails.getUsername();
        cartService.addToCart(id, user);
        return "redirect:/pizza";
    }

    @DeleteMapping("/removeitem/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        cartItemService.deleteById(id);
        return "redirect:/cart";
    }

    @PatchMapping("/changeitemamount")
    public String changeItemAmount( @RequestParam("id") Long itemId, @RequestParam("amount") Long amount) {
        cartItemService.changeItemAmount(itemId, amount);
        return "redirect:/cart";
    }

}
