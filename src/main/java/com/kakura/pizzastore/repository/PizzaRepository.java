package com.kakura.pizzastore.repository;

import com.kakura.pizzastore.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
