package com.group3.camping_project.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ChargeRequest implements Serializable {
    @Id
    @GeneratedValue
    private int id;
    private String cardNumber;
    private String exp_month;
    private String exp_year;
    private String cvv;
    private String name;
    private long amount;
    private String currency;
    private String description;
    private String email;
    private String phone;
    @Column(updatable = false)
    private LocalDateTime createdAt ;
    @PrePersist
    public void create(){
        createdAt = LocalDateTime.now();

    }




}
