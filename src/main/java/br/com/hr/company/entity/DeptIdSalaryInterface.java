package br.com.hr.company.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeptIdSalaryInterface {

    private Integer deptId;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;

}
