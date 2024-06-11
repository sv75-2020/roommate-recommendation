package com.ftn.sbnz.model.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LocalDate created;
    private boolean paidDeposit;
    @ManyToOne
    private Accommodation accommodation;
    @OneToOne
    private Roommates roommates;
    @ManyToMany
    private List<Payment> payments;
    @ManyToMany
    private List<DepositPayment> depositPayments;
    private ReservationStatus status;

    public Reservation(LocalDate now, boolean b, Accommodation a1, Roommates rm1, ArrayList<Payment> payments, ArrayList<DepositPayment> depositPayments, ReservationStatus pending) {
        this.created=now;
        this.paidDeposit=b;
        this.accommodation=a1;
        this.roommates=rm1;
        this.payments=payments;
        this.depositPayments=depositPayments;
        this.status=pending;
    }
}
