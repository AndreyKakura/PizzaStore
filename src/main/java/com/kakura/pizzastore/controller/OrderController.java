package com.kakura.pizzastore.controller;

import com.kakura.pizzastore.model.Cart;
import com.kakura.pizzastore.model.Order;
import com.kakura.pizzastore.security.CustomUserDetails;
import com.kakura.pizzastore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public String createOrder(@RequestParam("address") @NotEmpty String address, @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.createOrder(address, userDetails.getUser().getId());
        return "redirect:/orders";
    }

    @GetMapping()
    public String index(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        List<Order> orders = orderService.findAllByUserId(userDetails.getUser().getId());
        model.addAttribute("orders", orders);
        return "orders/index";
    }
}
