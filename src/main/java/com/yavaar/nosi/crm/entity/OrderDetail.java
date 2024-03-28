package com.yavaar.nosi.crm.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    @JoinColumns(
            {
                    @JoinColumn(updatable = true, insertable = true, name = "product_id", referencedColumnName = "id"),
                    @JoinColumn(updatable = true, insertable = true, name = "product_sku", referencedColumnName = "sku"),
                    @JoinColumn(updatable = true, insertable = true, name = "product_name", referencedColumnName = "name"),
                    @JoinColumn(updatable = true, insertable = true, name = "product_price", referencedColumnName = "price")
            }
    )
    private Product product;

    private int quantity;
    private BigDecimal subtotal;

    public OrderDetail() {
    }

    public OrderDetail(Product product, int quantity, BigDecimal subtotal) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = subtotal;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OrderDetail that)) return false;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(subtotal, that.subtotal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, subtotal);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }

}
