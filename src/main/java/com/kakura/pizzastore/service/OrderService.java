package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.*;
import com.kakura.pizzastore.repository.CartRepository;
import com.kakura.pizzastore.repository.OrderItemRepository;
import com.kakura.pizzastore.repository.OrderRepository;
import com.kakura.pizzastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Transactional
    public void createOrder(String address, Long userId) {
        Cart cart = cartRepository.findCartByUserId(userId).get();
        List<CartItem> cartItems = cart.getCartItems();
        Order order = new Order();
        List<OrderItem> orderItems = convertCartItemsToOrderItems(cartItems, order);
        order.setCreated(LocalDateTime.now());
        order.setUpdated(LocalDateTime.now());
        order.setPrice(calculateTotalPrice(cart));
        order.setAddress(address);
        order.setOrderItems(orderItems);
        order.setOrderStatus(OrderStatus.NEW);

        User user = userRepository.findById(userId).get();
        order.setUser(user);

        orderItemRepository.saveAll(orderItems);
        orderRepository.save(order);
        userRepository.save(user);

        cartRepository.deleteById(cart.getId());
    }

    public List<Order> findAllByUserIdSortedByCreatedDesc(Long userId) {
        return orderRepository.findAllByUserId(userId, Sort.by(Sort.Direction.DESC, "created"));
    }

    private List<OrderItem> convertCartItemsToOrderItems(List<CartItem> cartItems, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPizza(cartItem.getPizza());
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        List<CartItem> items = cart.getCartItems();
        long totalPrice = 0;
        for (CartItem item : cart.getCartItems()) {
            totalPrice += item.calculatePrice();
        }
        return BigDecimal.valueOf(totalPrice);
    }

}