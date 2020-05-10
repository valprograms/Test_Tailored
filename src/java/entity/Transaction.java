/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import util.enumeration.PaymentTypeEnum;

/**
 *
 * @author Kaijing
 */
@Entity
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date transactionDateTime;
    
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentTypeEnum;
    
    @Column(columnDefinition = "boolean NOT NULL default false")
    @NotNull
    private boolean isPaid;
    
    @Column(nullable = true, precision = 2)
    @Null
    @Size(min=1,max=15)
    private String transactionNumber;
          
    @OneToMany(mappedBy = "transaction")
    private List<OrderHistory> orders;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Customer customer;

    public Transaction() {
        orders = new ArrayList<OrderHistory>();
    }

    public Transaction(Date transactionDateTime, PaymentTypeEnum paymentTypeEnum, boolean isPaid, String transactionNumber) {
        this.transactionDateTime = transactionDateTime;
        this.paymentTypeEnum = paymentTypeEnum;
        this.isPaid = isPaid;
        this.transactionNumber = transactionNumber;
        orders = new ArrayList<OrderHistory>();
    }
    
    
    
    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionId != null ? transactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the transactionId fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.transactionId == null && other.transactionId != null) || (this.transactionId != null && !this.transactionId.equals(other.transactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transaction[ id=" + transactionId + " ]";
    }

    /**
     * @return the transactionDateTime
     */
    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * @param transactionDateTime the transactionDateTime to set
     */
    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    /**
     * @return the paymentTypeEnum
     */
    public PaymentTypeEnum getPaymentTypeEnum() {
        return paymentTypeEnum;
    }

    /**
     * @param paymentTypeEnum the paymentTypeEnum to set
     */
    public void setPaymentTypeEnum(PaymentTypeEnum paymentTypeEnum) {
        this.paymentTypeEnum = paymentTypeEnum;
    }

    /**
     * @return the isPaid
     */
    public boolean isIsPaid() {
        return isPaid;
    }

    /**
     * @param isPaid the isPaid to set
     */
    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    /**
     * @return the transactionNumber
     */
    public String getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * @param transactionNumber the transactionNumber to set
     */
    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
    
    public List<OrderHistory> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderHistory> orders) {
        this.orders = orders;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
