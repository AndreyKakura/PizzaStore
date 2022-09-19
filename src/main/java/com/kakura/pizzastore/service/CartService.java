package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.Cart;
import com.kakura.pizzastore.model.CartItem;
import com.kakura.pizzastore.repository.CartItemRepository;
import com.kakura.pizzastore.repository.CartRepository;
import com.kakura.pizzastore.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final PizzaRepository pizzaRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, PizzaRepository pizzaRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.pizzaRepository = pizzaRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void addItem(Long pizzaId, long amount, String user) {
        Cart cart = cartRepository.findCartByUserEmail(user).get();
        List<CartItem> items = cart.getOrderItems();
        boolean cartContainsProduct = false;
        boolean amountGreaterThanZero = amount > 0;

        for (CartItem item : items) {
            if (item.getPizza().getId().equals(pizzaId)) {
                if (amountGreaterThanZero) {
                    item.setAmount(amount);
                    cartItemRepository.save(item);
                    cartContainsProduct = true;
                } else {
                    System.out.println(item.getId());
                    cartItemRepository.deleteById(item.getId());
                    System.out.println(cartItemRepository.findById(item.getId()).get());
                }
                break;
            }
        }
//        for (CartItem item : items) {
//            if (item.getPizza().getId().equals(pizzaId)) {
//                if (amount > 0) {
//                    item.setAmount(amount);
//                    cartItemRepository.save(item);
//                } else {
//                    System.out.println(item.getId());
//                    cartItemRepository.deleteById(item.getId());
//                    System.out.println(cartItemRepository.findById(item.getId()).get());
//                }
//                cartContainsProduct = true;
//            }
//        }
        if (!cartContainsProduct && amountGreaterThanZero) {
            CartItem item = new CartItem();
            item.setPizza(pizzaRepository.findById(pizzaId).get());
            item.setAmount(amount);
            item.setCart(cart);
            cart.getOrderItems().add(item);
            cartRepository.save(cart);
        }
    }

    public Map<Long, Long> getMapOfPizzaIdAndAmountForUser(String user) {
        List<CartItem> items = cartRepository.findCartByUserEmail(user).get().getOrderItems();
        Map<Long, Long> mapOfIdAndAmount = new HashMap<>();
        for (CartItem item : items) {
            mapOfIdAndAmount.put(item.getPizza().getId(), item.getAmount());
        }
        return mapOfIdAndAmount;
    }
}
