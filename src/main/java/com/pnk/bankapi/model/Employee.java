package com.pnk.bankapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "active_status")
    @PositiveOrZero
    private int activeStatus;

    @Column(name = "created_date")
    @CreationTimestamp
    @PastOrPresent
    private Date createdDate;

    @Column(name = "last_updated_date")
    @PastOrPresent
    @UpdateTimestamp
    private Date lastUpdatedDate;

    public Employee(Long employeeId, String employeeName, int activeStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.activeStatus = activeStatus;
    }
}