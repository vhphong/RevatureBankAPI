package com.pnk.bankapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;        // patterned: tablenameId

    @Column(name = "name")
    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 100, message = "Name can not be more than 100 characters.")
    private String name;

    @Column(name = "email")
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    @Size(max = 100, message = "Email can not be more than 100 characters.")
    private String email;

    @Column(name = "dob")
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate dob;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    @NotEmpty(message = "Address cannot be empty")
    @Size(max = 100, message = "Address can not be more than 100 characters.")
    private String address;

    @Column(name = "password")
    @NotEmpty(message = "Password cannot be empty")
    @Size(max = 100, message = "Password can not be more than 100 characters.")
    private String password;

    @Column(name = "created_date")
    @CreationTimestamp
    @PastOrPresent
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @PastOrPresent
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("customer")
    private List<Account> accountList = new ArrayList<>();


    public Customer(Long customerId, String name, List<Account> accountList) {
        this.customerId = customerId;
        this.name = name;
        this.accountList = accountList;
    }

    public Customer(Long customerId, String name, String email, String password) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}