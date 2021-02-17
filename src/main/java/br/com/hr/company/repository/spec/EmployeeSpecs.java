package br.com.hr.company.repository.spec;

import br.com.hr.company.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class EmployeeSpecs {

    public static Specification<Employee> pesquisaDeptId(Integer deptId){
        return (root, query, builder) -> builder.equal(root.get("department"), deptId);
    }

    public static Specification<Employee> pesquisaSalarioMenor(BigDecimal salMin){
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("salary"),salMin);
    }

    public static Specification<Employee> pesquisaSalarioMaior(BigDecimal salMax){
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("salary"),salMax);
    }
}
