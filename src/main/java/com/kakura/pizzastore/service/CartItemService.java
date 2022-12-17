package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.CartItem;
import com.kakura.pizzastore.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Transactional
    public CartItem changeItemAmount(Long itemId, Long amount) {
            CartItem itemToBeUpdated = cartItemRepository.findById(itemId).get();
            itemToBeUpdated.setAmount(amount);
            return cartItemRepository.save(itemToBeUpdated);
    }
}
