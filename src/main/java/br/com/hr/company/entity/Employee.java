package br.com.hr.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employees", schema = "HR")
@Data
public class Employee implements Serializable {
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date", nullable = false, columnDefinition = "date")
    private Date hireDate;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "commission_pct")
    private Long commissionPct;

    @Column(name = "manager_id")
    private Integer managerId;

    @JsonIgnoreProperties(value = "department_name", allowGetters = true)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department;

}
