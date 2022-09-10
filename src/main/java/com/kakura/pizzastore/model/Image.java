package com.kakura.pizzastore.model;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    @Lob
    private byte[] bytes;

    @OneToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(name, image.name) && Objects.equals(originalFileName, image.originalFileName) && Objects.equals(size, image.size) && Objects.equals(contentType, image.contentType) && Arrays.equals(bytes, image.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, originalFileName, size, contentType);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", size=" + size +
                ", contentType='" + contentType + '\'' +
                ", bytes=" + Arrays.toString(bytes) +
                ", pizza=" + pizza +
                '}';
    }
}
