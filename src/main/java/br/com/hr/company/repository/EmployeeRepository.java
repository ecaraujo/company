package br.com.hr.company.repository;

import br.com.hr.company.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    @Override
    Page<Employee> findAll(Pageable pageable);

    @Query("select p from Employee p where p.employeeId = ?1")
    Employee findEmployeeId(Long employeeId);

    @Query("select p from Employee p where p.managerId = ?1")
    List<Employee> findEmployeesByManagerId(Integer managerId);

    List<Employee> consultaPorManagerId(Integer managerId);

    List<Employee> findEmployeeByDeparmentId(Integer deptId);

    List<Employee> findEmployeeBySalaryAndDeptId(Integer deptId, BigDecimal salMin, BigDecimal salMax);

    List<Employee> findEmployeeWithCriteria(Integer deptId, BigDecimal salMin, BigDecimal salMax);

}
