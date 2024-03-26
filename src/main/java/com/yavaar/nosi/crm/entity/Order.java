package com.yavaar.nosi.crm.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "customerorder")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumns(
            {
                    @JoinColumn(updatable = true, insertable = true, name = "customer_id", referencedColumnName = "id"),
                    @JoinColumn(updatable = true, insertable = true, name = "first_name", referencedColumnName = "first_name"),
                    @JoinColumn(updatable = true, insertable = true, name = "last_name", referencedColumnName = "last_name")
            }
    )
    private Customer customer;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @Column(name = "order_amount")
    private BigDecimal orderAmount;
    @Column(name = "tax_amount")
    private BigDecimal taxAmount;
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public Order() {
    }

    public Order(LocalDate orderDate, BigDecimal orderAmount, BigDecimal taxAmount, BigDecimal totalAmount, PaymentType paymentType) {
        this.orderDate = orderDate;
        this.orderAmount = orderAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Order order)) return false;
        return Objects.equals(id, order.id) && Objects.equals(orderDate, order.orderDate) && Objects.equals(orderAmount, order.orderAmount) && Objects.equals(taxAmount, order.taxAmount) && Objects.equals(totalAmount, order.totalAmount) && paymentType == order.paymentType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate, orderAmount, taxAmount, totalAmount, paymentType);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", orderAmount=" + orderAmount +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", paymentType=" + paymentType +
                '}';
    }

}
