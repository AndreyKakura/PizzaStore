package com.kakura.pizzastore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Pizza pizza;

    @Column(name = "amount")
    @Min(value = 0, message = "amount should be greater than 0")
    private Long amount;

    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long calculatePrice() {
        return this.getPizza().getPrice().longValue() * this.getAmount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item = (CartItem) o;
        return Objects.equals(id, item.id) && Objects.equals(cart, item.cart) && Objects.equals(pizza, item.pizza) && Objects.equals(amount, item.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, pizza, amount);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", pizza=" + pizza +
                ", amount=" + amount +
                '}';
    }
}
