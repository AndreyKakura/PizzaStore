package com.kakura.pizzastore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private Pizza pizza;

    @Column(name = "amount")
    @Min(value = 0, message = "Amount should be greater than 0")
    private Long amount;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem item = (OrderItem) o;
        return Objects.equals(id, item.id) && Objects.equals(pizza, item.pizza) && Objects.equals(amount, item.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pizza, amount);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", pizza=" + pizza +
                ", amount=" + amount +
                '}';
    }
}
