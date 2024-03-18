package com.pnk.bankapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pnk.bankapi.model.Account;
import com.pnk.bankapi.model.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CustomerDTO {

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


    public CustomerDTO build(Customer customer) {
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.dob = customer.getDob();
        this.phone = customer.getPhone();
        this.address = customer.getAddress();
        this.password = customer.getPassword();
        this.createdDate = customer.getCreatedDate();
        this.lastUpdatedDate = customer.getLastUpdatedDate();
        this.accountList = customer.getAccountList();

        return this;
    }
}
