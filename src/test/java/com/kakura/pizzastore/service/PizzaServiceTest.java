package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.Pizza;
import com.kakura.pizzastore.repository.ImageRepository;
import com.kakura.pizzastore.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PizzaServiceTest {

    @InjectMocks
    PizzaService pizzaService;

    @Mock
    PizzaRepository pizzaRepository;

    @Mock
    ImageRepository imageRepository;

    List<Pizza> pizzas = new ArrayList<>();

    @Test
    void findAll() {
        pizzas.add(new Pizza());
        pizzas.add(new Pizza());
        pizzas.add(new Pizza());
        when(pizzaRepository.findAll()).thenReturn(pizzas);
        List<Pizza> pizzaList = pizzaService.findAll();
        assertEquals(3, pizzaList.size());
        verify(pizzaRepository, times(1)).findAll();
    }

    @Test
    void findOne() {
        Pizza pizza = new Pizza();
        pizza.setId(1L);
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        Pizza pizzaFromService = pizzaService.findOne(1L);
        assertTrue(pizzaFromService != null);
        verify(pizzaRepository, times(1)).findById(1L);
    }

    @Test
    void findOneNull() {
        when(pizzaRepository.findById(1L)).thenReturn(Optional.empty());
        Pizza pizzaFromService = pizzaService.findOne(1L);
        assertTrue(pizzaFromService == null);
        verify(pizzaRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        Pizza pizza = new Pizza();
        byte[] bytes = {};
        MultipartFile file = new MockMultipartFile("fileName", "originalFileName","image/png", bytes);
        pizzaService.save(pizza, file);
        verify(pizzaRepository, times(1)).save(pizza);
    }

    @Test
    void update() {
        Pizza pizza = new Pizza();
        when(pizzaRepository.findById(1L)).thenReturn(Optional.of(pizza));
        byte[] bytes = {};
        MultipartFile file = new MockMultipartFile("fileName", "originalFileName","image/png", bytes);
        pizzaService.update(1L, pizza, file);
        verify(pizzaRepository, times(1)).save(pizza);
    }
}