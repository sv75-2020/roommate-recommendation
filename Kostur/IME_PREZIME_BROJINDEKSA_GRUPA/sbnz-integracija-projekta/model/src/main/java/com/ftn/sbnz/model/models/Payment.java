package com.ftn.sbnz.model.models;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import org.apache.tools.ant.taskdefs.Local;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LocalDate paymentDue;
    private boolean paidRoommate1;
    private boolean paidRoommate2;
    @ManyToOne
    private Reservation reservation;
}
