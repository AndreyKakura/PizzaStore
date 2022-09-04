package com.kakura.pizzastore.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 1, max = 30, message = "Name should be between 1 and 30 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 1, max = 30, message = "Surname should be between 1 and 30 characters")
    @Column(name = "surname")
    private String surname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Enter email in \"emailuser@domain\" format")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user",cascade = CascadeType.REMOVE)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && role == user.role && Objects.equals(cart, user.cart) && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, role, cart, orders);
    }

    @Override
    public String
    toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", cart=" + cart +
                ", orders=" + orders +
                '}';
    }
}
