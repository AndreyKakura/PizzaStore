package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.Cart;
import com.kakura.pizzastore.model.CartItem;
import com.kakura.pizzastore.repository.CartItemRepository;
import com.kakura.pizzastore.repository.CartRepository;
import com.kakura.pizzastore.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
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

    @Transactional
    public void addToCart(Long pizzaId, Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId).get();
        List<CartItem> items = cart.getCartItems();
        boolean cartContainsProduct = false;
        for (CartItem item : items) {
            if (item.getPizza().getId().equals(pizzaId)) {
                item.setAmount(item.getAmount() + 1);
                cartItemRepository.save(item);
                cartContainsProduct = true;
                break;
            }
        }
        if (!cartContainsProduct) {
            CartItem item = new CartItem();
            item.setPizza(pizzaRepository.findById(pizzaId).get());
            item.setAmount(1L);
            item.setCart(cart);
            cart.getCartItems().add(item);
            cartRepository.save(cart);
        }
    }

    public Cart findOneByUserId(Long userId) {
        return cartRepository.findCartByUserId(userId).orElse(null);
    }

    public Map<Long, Long> getMapOfPizzaIdAndAmountForUser(Long userId) {
        List<CartItem> items = cartRepository.findCartByUserId(userId).get().getCartItems();
        Map<Long, Long> mapOfIdAndAmount = new HashMap<>();
        for (CartItem item : items) {
            mapOfIdAndAmount.put(item.getPizza().getId(), item.getAmount());
        }
        return mapOfIdAndAmount;
    }

    public Long calculateTotalPrice(Cart cart) {
        long totalPrice = 0;
        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.calculatePrice();
        }
        return totalPrice;
    }
}
