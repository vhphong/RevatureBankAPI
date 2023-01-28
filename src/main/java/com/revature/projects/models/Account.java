package com.revature.projects.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;     // patterned: tablenameId

    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "balance")
    private double balance;

    @Column(name = "opening_date")
    private Date dateOfOpening;

    @Column(name = "type")
    private String type;

    @Column(name = "account_active_status")
    private int accountActiveStatus;
}