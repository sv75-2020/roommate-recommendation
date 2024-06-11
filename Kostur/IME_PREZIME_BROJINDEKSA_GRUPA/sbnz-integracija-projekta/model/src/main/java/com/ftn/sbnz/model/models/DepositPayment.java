package com.ftn.sbnz.model.models;

import javax.persistence.*;

import com.ftn.sbnz.model.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DepositPayment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @ManyToOne
    private User user;
    private boolean paid;

    public DepositPayment(User user,boolean paid){
        this.user=user;
        this.paid=paid;
    }
}
