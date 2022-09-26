package com.kakura.pizzastore.service;

import com.kakura.pizzastore.model.Image;
import com.kakura.pizzastore.model.Pizza;
import com.kakura.pizzastore.repository.ImageRepository;
import com.kakura.pizzastore.repository.PizzaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PizzaService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PizzaRepository pizzaRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, ImageRepository imageRepository) {
        this.pizzaRepository = pizzaRepository;
        this.imageRepository = imageRepository;
    }

    public List<Pizza> findAll() {
        return pizzaRepository.findAll();
    }

    public Pizza findOne(Long id) {
        return pizzaRepository.findById(id).orElse(null);
    }

    public void save(Pizza pizza, MultipartFile imageFile) {
        if (imageFile.getSize() != 0) {
            Image image = toImageEntity(imageFile);
            pizza.setImage(image);
        }
        pizzaRepository.save(pizza);
    }

    public void update(Long id, Pizza updatedPizza, MultipartFile imageFile) {
        Pizza pizzaToBeUpdated = pizzaRepository.findById(id).get();
        updatedPizza.setId(id);
        if (imageFile.getSize() != 0) {
            Image image = toImageEntity(imageFile);
            if (pizzaToBeUpdated.getImage() != null) {
                image.setId(pizzaToBeUpdated.getImage().getId());
            }
            imageRepository.save(image);
            updatedPizza.setImage(image);
        }
        pizzaRepository.save(updatedPizza);
    }

    private Image toImageEntity(MultipartFile file) {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            LOGGER.error("Error has occurred while converting file to byte array", e);
        }
        return image;
    }
}
