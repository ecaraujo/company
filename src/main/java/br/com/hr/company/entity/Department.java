package br.com.hr.company.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "departments")
@Data
public class Department implements Serializable {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "manager_id")
    private Integer managerId;

    @Column(name = "location_id")
    private Integer locationId;

}
