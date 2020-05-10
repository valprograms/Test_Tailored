/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import util.enumeration.OrderStatusEnum;

/**
 *
 * @author Kaijing
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class OrderHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long orderId;
    
    @Column(nullable = false,precision = 2)
    @NotNull
    @Min(1)
    @Max(30)
    protected int quantity;
    
    @Enumerated(EnumType.STRING)
    protected OrderStatusEnum orderStatusEnum;
    
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = true)
    private Transaction transaction;

    public OrderHistory() {
    }

    public OrderHistory(int quantity, OrderStatusEnum orderStatusEnum) {
        this.quantity = quantity;
        this.orderStatusEnum = orderStatusEnum;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the orderStatusEnum
     */
    public OrderStatusEnum getOrderStatusEnum() {
        return orderStatusEnum;
    }

    /**
     * @param orderStatusEnum the orderStatusEnum to set
     */
    public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
}
