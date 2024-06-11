package com.ftn.sbnz.model.events;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.ftn.sbnz.model.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Timestamp("paymentDate")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BillPaidEvent {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LocalDate paymentDate;
    @ManyToOne
    private User user;
}
