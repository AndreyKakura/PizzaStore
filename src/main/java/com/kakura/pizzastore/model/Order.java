package com.kakura.pizzastore.model;

import com.kakura.pizzastore.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    //todo create class OrderItem, add field List<OrderItem>

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime created;

    @Column(name = "updated")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime updated;

    @Column(name = "price")
    @Positive(message = "Price should be grater than 0")
    private BigDecimal price;

    @Column(name = "address")
    @NotEmpty(message = "Address should not be empty")
    @Size(max = 100, message = "Address should not be greater than 100 characters")
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (user.getOrders() == null) {
            user.setOrders(List.of(this));
        } else {
            user.getOrders().add(this);
        }
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(created, order.created) && Objects.equals(updated, order.updated) && Objects.equals(price, order.price) && Objects.equals(address, order.address) && Objects.equals(user, order.user) && Objects.equals(orderItems, order.orderItems) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, price, address, user, orderItems, orderStatus);
    }

    @Override
    public String
    toString() {
        return "Order{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", sum=" + price +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", orderItems=" + orderItems +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
