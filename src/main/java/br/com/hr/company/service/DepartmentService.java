package br.com.hr.company.service;

import br.com.hr.company.entity.Department;
import br.com.hr.company.entity.Employee;
import br.com.hr.company.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository repository;

    public Department saveDepatment(Department department){

        return repository.save(department);

    }

    public Department findDepartmentById(Long departmentId){

        return repository.findById(departmentId).orElseThrow();

    }
}
