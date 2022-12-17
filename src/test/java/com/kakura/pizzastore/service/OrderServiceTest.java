package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.*;
import com.kakura.pizzastore.repository.CartRepository;
import com.kakura.pizzastore.repository.OrderItemRepository;
import com.kakura.pizzastore.repository.OrderRepository;
import com.kakura.pizzastore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void createOrder() {
        when(orderRepository.save(any(Order.class))).thenAnswer(new Answer<Order>() {
            long sequence = 1;

            @Override
            public Order answer(InvocationOnMock invocationOnMock) throws Throwable {
                Order order = (Order) invocationOnMock.getArgument(0);
                order.setId(sequence++);
                return order;
            }
        });


        when(cartRepository.findCartByUserId(anyLong())).thenAnswer(new Answer<Optional<Cart>>() {
            long sequence = 1;

            @Override
            public Optional<Cart> answer(InvocationOnMock invocationOnMock) throws Throwable {
                Pizza pizza = new Pizza();
                pizza.setPrice(BigDecimal.valueOf(10));
                CartItem cartItem = new CartItem();
                cartItem.setPizza(pizza);
                cartItem.setAmount(1L);
                Cart cart = new Cart();
                cart.setCartItems(List.of(cartItem));
                cart.setId(sequence++);
                return Optional.of(cart);
            }
        });

        when(userRepository.findById(anyLong())).thenAnswer(new Answer<Optional<User>>() {
            long sequence = 1;

            @Override
            public Optional<User> answer(InvocationOnMock invocationOnMock) throws Throwable {
                User user = new User();
                user.setId(sequence++);
                return Optional.of(user);
            }
        });

        Order createdOrder = orderService.createOrder("Address", 1L);

        assertNotNull(createdOrder.getId());
    }

    @Test
    void findAllByUserIdSortedByCreatedDesc() {
        when(orderRepository.findAllByUserId(anyLong(),any(Sort.class)))
                .thenAnswer(new Answer<List<Order>>() {
                    long sequence = 1;
                    @Override
                    public List<Order> answer(InvocationOnMock invocationOnMock) throws Throwable {
                        Order order = new Order();
                        order.setId(sequence++);
                        return List.of(order);
                    }
                });

        List<Order> orders = orderService.findAllByUserIdSortedByCreatedDesc(1L);

        assertNotNull(orders.get(0).getId());
    }
}