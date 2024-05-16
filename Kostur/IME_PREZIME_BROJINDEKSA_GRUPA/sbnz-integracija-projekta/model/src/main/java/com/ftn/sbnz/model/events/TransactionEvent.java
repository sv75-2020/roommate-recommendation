package com.ftn.sbnz.model.events;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
public class TransactionEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date executionTime;
    private Long customerId;
    private Double totalAmount; 
    public String Phone;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public TransactionEvent() {
        super();
    }
    
    public TransactionEvent(Long customerId, Double totalAmount) {
        super();
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public TransactionEvent(Long customerId, Double totalAmount, String phone) {
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        Phone = phone;
    }

    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}

