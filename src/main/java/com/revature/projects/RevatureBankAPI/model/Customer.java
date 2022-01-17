package com.revature.projects.RevatureBankAPI.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Column(name = "password")
    private String password;

    public Customer() {
        super();
    }

    public Customer(String name, String email, Date dob, String mobile, String address, String password) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.mobile = mobile;
        this.address = address;
        this.password = password;
    }

    public Long getId() {
        return customerId;
    }

    public void setId(Long id) {
        this.customerId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer [id=" + customerId + ", name=" + name + ", email=" + email + ", dob=" + dob + ", mobile=" + mobile
                + ", address=" + address + ", password=" + password + "]";
    }

}
