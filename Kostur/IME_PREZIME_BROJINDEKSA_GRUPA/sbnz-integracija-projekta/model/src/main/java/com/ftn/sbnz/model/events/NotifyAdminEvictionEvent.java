package com.ftn.sbnz.model.events;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

import com.ftn.sbnz.model.models.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotifyAdminEvictionEvent {

     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private User user;
    private LocalDate executionTime;
  
    public NotifyAdminEvictionEvent(User user,LocalDate date){
        this.user=user;
        this.executionTime=date;
    }
}
