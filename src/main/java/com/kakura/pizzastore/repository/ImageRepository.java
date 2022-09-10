package com.kakura.pizzastore.repository;

import com.kakura.pizzastore.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
