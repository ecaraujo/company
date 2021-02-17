package br.com.hr.company.service;

import static br.com.hr.company.repository.spec.EmployeeSpecs.*;
import br.com.hr.company.entity.Employee;
import br.com.hr.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Page<Employee> findAll(Integer pageInitial, Integer pageFinal){

        Pageable pagination = PageRequest.of(pageInitial, pageFinal, Sort.Direction.ASC, "employeeId");

        return repository.findAll(pagination);

    }

    public Employee findEmployeeById(Long employeeId) {
        return repository.findEmployeeId(employeeId);
    }

    public List<Employee> findEmployeesByManagerId(Integer managerId){
        return repository.findEmployeesByManagerId(managerId);
    }

    public List<Employee> consultaPorManagerId(Integer managerId){
        return repository.findEmployeesByManagerId(managerId);
    }

    public List<Employee> findEmployeeByDeparmentId(Integer deptId){
        return repository.findEmployeeByDeparmentId(deptId);
    }

    public List<Employee> findEmployeeBySalaryAndDeptId(Integer deptId, BigDecimal salMin, BigDecimal salMax){
        return repository.findEmployeeBySalaryAndDeptId(deptId, salMin, salMax);
    };

    public List<Employee> findEmployeeWithCriteria(Integer deptId, BigDecimal salMin, BigDecimal salMax){
        return repository.findEmployeeWithCriteria(deptId, salMin, salMax);
    };

    public List<Employee> findEmployeeWithCriteriaSpec(Integer deptId, BigDecimal salMin, BigDecimal salMax){

        return repository.findAll(pesquisaDeptId(deptId).and(pesquisaSalarioMenor(salMin)).and(pesquisaSalarioMaior(salMax)));
    };

    public Employee saveEmployee(Employee employee){
        if(employee.getFirstName() == null){
            throw new IllegalArgumentException("O nome não deve ser vazio");
        }
        return repository.save(employee);
    }

    public void deleteEmployee(Employee employee){
        repository.delete(employee);
    }
}
