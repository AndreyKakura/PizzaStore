package com.kakura.pizzastore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "orders_details")
public class OrderDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id", referencedColumnName = "id")
    private  Pizza pizza;

    @Column(name = "amount")
    @Min(value = 0, message = "amount should be greater than 0")
    private Long amount;

    @Column(name = "price")
    @Positive(message = "Price should be greater than 0")
    private BigDecimal price;

    public OrderDetails() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(order, that.order) && Objects.equals(pizza, that.pizza) && Objects.equals(amount, that.amount) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, pizza, amount, price);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", order=" + order +
                ", pizza=" + pizza +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
