package br.com.hr.company.repository.impl;

import br.com.hr.company.entity.Employee;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl {

    @PersistenceContext
    private EntityManager manager;

    public List<Employee> findEmployeeByDeparmentId(Integer deptId){

        String jpql = "from Employee where department_id = :deptId";

        return manager.createQuery(jpql, Employee.class)
                .setParameter("deptId", deptId)
                .getResultList();
    }

    public List<Employee> findEmployeeBySalaryAndDeptId(Integer deptId, BigDecimal salMin, BigDecimal salMax){

        StringBuilder jpql = new StringBuilder();

        jpql.append("from Employee where 0 = 0 ");

        var parameters = new HashMap<String, Object>();

        if(deptId != null){
            jpql.append(" and department_id = :deptId ");
            parameters.put("deptId",deptId);
        }

        if(salMin != null){
            jpql.append("and salary >= :salaryMin ");
            parameters.put("salaryMin", salMin);
        }

        if(salMax != null){
            jpql.append("and salary <= :salaryMax");
            parameters.put("salaryMax", salMax);
        }

        TypedQuery<Employee> query = manager.createQuery(jpql.toString(), Employee.class);

        parameters.forEach((key, value) -> query.setParameter(key, value));

        return query.getResultList();
    }

    public List<Employee> findEmployeeWithCriteria(Integer deptId, BigDecimal salMin, BigDecimal salMax){

        var predicates = new ArrayList<Predicate>();

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);

        if(deptId != null) {
            predicates.add((builder.equal(root.get("department"), deptId)));
        }

        if(salMin != null){
            predicates.add(builder.greaterThanOrEqualTo(root.get("salary"), salMin));
        }

        if(salMax != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("salary"), salMax));
        }

        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Employee> query = manager.createQuery(criteria);

        return query.getResultList();

    }

}
