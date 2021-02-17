package br.com.hr.company.controller;

import br.com.hr.company.entity.DeptIdSalaryInterface;
import br.com.hr.company.entity.Employee;
import br.com.hr.company.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/hr")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/employees")
    public ResponseEntity<Page<Employee>> findAll(@RequestHeader(name = "pagenumber", defaultValue = "1") String pageNumber,
                                                  @RequestHeader(name = "pagesize", defaultValue = "5") String pageSize){

        Page<Employee> employees = service.findAll(Integer.parseInt(pageNumber), Integer.parseInt(pageSize));

        return ResponseEntity.ok().body(employees);
    }
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> findEmployeeById(@PathVariable("id") Long employeeId) {
        Employee employee = service.findEmployeeById(employeeId);
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/manager/{id}/employees")
    public ResponseEntity<List<Employee>> findEmployeesByManagerId(@PathVariable("id") Integer managerId){
        List<Employee> employees = service.findEmployeesByManagerId(managerId);
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<List<Employee>> findEmployeeByDeparmentId(@PathVariable("id") Integer departmantId){
        List<Employee> employees = service.findEmployeeByDeparmentId(departmantId);
        return ResponseEntity.ok().body(employees);
    }


    @GetMapping("/manager/{id}")
    public ResponseEntity<List<Employee>> consultaPorManagerId(@PathVariable("id") Integer managerId){
        List<Employee> employees = service.findEmployeesByManagerId(managerId);
        return ResponseEntity.ok().body(employees);
    }
    @GetMapping("/manager/info")
    @ResponseBody
    public ResponseEntity<List<Employee>> findEmployeeBySalaryAndDeptId(@RequestBody DeptIdSalaryInterface request){

        List<Employee> response = service.findEmployeeBySalaryAndDeptId(request.getDeptId(), request.getSalaryMin(), request.getSalaryMax());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/manager/info2")
    @ResponseBody
    public ResponseEntity<List<Employee>> findEmployeeWithCriteria(@RequestBody DeptIdSalaryInterface request){

        List<Employee> response = service.findEmployeeWithCriteria(request.getDeptId(), request.getSalaryMin(), request.getSalaryMax());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/manager/info3")
    @ResponseBody
    public ResponseEntity<List<Employee>> findEmployeeWithCriteriaSpec(@RequestBody DeptIdSalaryInterface request){

        List<Employee> response = service.findEmployeeWithCriteriaSpec(request.getDeptId(), request.getSalaryMin(), request.getSalaryMax());
        return ResponseEntity.ok().body(response);
    }

}
