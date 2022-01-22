package com.revature.projects.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "cust_id")
    private Long custId;

    @Column(name = "balance")
    private double balance;

    @Column(name = "opening_date")
    private Date dateOfOpening;

    @Column(name = "status")
    private String status;

}
