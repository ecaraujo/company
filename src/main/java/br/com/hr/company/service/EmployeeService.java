package br.com.hr.company.service;

import static br.com.hr.company.repository.spec.EmployeeSpecs.*;

import br.com.hr.company.entity.Department;
import br.com.hr.company.entity.Employee;
import br.com.hr.company.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private DepartmentService departmentService;

    public Page<Employee> findAll(Integer pageInitial, Integer pageFinal){

        Pageable pagination = PageRequest.of(pageInitial, pageFinal, Sort.Direction.ASC, "employeeId");

        return repository.findAll(pagination);

    }

    public Employee findEmployeeById(Long employeeId) {
        return repository.findEmployeeId(employeeId);
    }

    public List<Employee> findEmployeesByManagerId(Integer managerId){

        List<Employee> generico = new ArrayList<>();

        generico = repository.findEmployeesByManagerId(managerId);

        List<Employee> startWithE = repository.findEmployeesByManagerId(managerId)
                .stream()
                .filter(p -> p.getFirstName().startsWith("E"))
                .collect(Collectors.toList());

        Collections.sort(generico, new Comparator<Employee>() {

            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.getHireDate().compareTo(o2.getHireDate());
            }
        });

        //return repository.findEmployeesByManagerId(managerId);

        return startWithE;
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
    @Transactional
    public Employee saveEmployee(Employee employee){
        if(employee.getFirstName() == null){
            throw new IllegalArgumentException("O nome não deve ser vazio");
        }

        Optional<Department> department = Optional.of(departmentService.findDepartmentById(employee.getDepartment().getDepartmentId()));

        Runnable r = () -> System.out.println("Thread com função lambda!");
        new Thread(r).start();

        employee.setDepartment(department.orElseThrow(() -> new RuntimeException(employee.getDepartment().getDepartmentId().toString())));

        return repository.save(employee);
    }

    public void deleteEmployee(Employee employee){
        repository.delete(employee);
    }
}
