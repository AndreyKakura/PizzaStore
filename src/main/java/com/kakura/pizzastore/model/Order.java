package com.kakura.pizzastore.model;

import com.kakura.pizzastore.converter.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order")
public class Order {

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

    @Column(name = "sum")
    @Positive(message = "Sum should be grater than 0")
    private BigDecimal sum;

    @Column(name = "address")
    @NotEmpty(message = "Address should not be empty")
    @Size(max = 100, message = "Address should not be greater than 100 characters")
    private String address;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetails> orderDetails;

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
        this.user = user;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
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
        return Objects.equals(id, order.id) && Objects.equals(created, order.created) && Objects.equals(updated, order.updated) && Objects.equals(user, order.user) && Objects.equals(sum, order.sum) && Objects.equals(address, order.address) && Objects.equals(orderDetails, order.orderDetails) && orderStatus == order.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, created, updated, user, sum, address, orderDetails, orderStatus);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", user=" + user +
                ", sum=" + sum +
                ", address='" + address + '\'' +
                ", orderDetails=" + orderDetails +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
