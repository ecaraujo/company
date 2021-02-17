package br.com.hr.company;


import br.com.hr.company.entity.Department;
import br.com.hr.company.entity.Employee;
import br.com.hr.company.service.DepartmentService;
import br.com.hr.company.service.EmployeeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = { "spring.config.location = classpath:application-test.yaml"})
class CompanyApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private EmployeeService serviceEmp;

    @Autowired
    private DepartmentService serviceDpt;

    @Before
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;

        System.out.println("Port: " + port );
    }


    @Test
    public void testaCadastroDeFuncionario(){

        Department dept = serviceDpt.findDepartmentById(90L);

        Employee emp = new Employee();
        emp.setEmployeeId(207L);
        emp.setFirstName("Edson");
        emp.setLastName("Araujo");
        emp.setJobId("IT_PROG");
        emp.setEmail("edson.araujo@ftd.com.br");
        emp.setSalary(new BigDecimal("9000"));
        emp.setDepartment(dept);
        emp.setHireDate(new Date());

        emp = serviceEmp.saveEmployee(emp);

        Assertions.assertThat(emp).isNotNull();
        Assertions.assertThat(emp.getEmployeeId()).isNotNull();

    }
    @org.junit.Test(expected = IllegalArgumentException.class)
    public void testaCadastrodeFuncionarioSemNome(){
        Employee emp = new Employee();

        Department dept = serviceDpt.findDepartmentById(90L);

        emp.setEmployeeId(207L);
        emp.setFirstName("Edson");
        emp.setLastName("Araujo");
        emp.setJobId("IT_PROG");
        emp.setEmail("edson.araujo@ftd.com.br");
        emp.setSalary(new BigDecimal("9000"));
        emp.setDepartment(dept);
        emp.setHireDate(new Date());
        emp = serviceEmp.saveEmployee(emp);

    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarEmpregado(){

        setUp();

        RestAssured.given()
                .basePath("/hr/employees")
                .accept(ContentType.JSON)
            .when()
                .get()
            .then()
                .statusCode(HttpStatus.OK.value());
    }
    @Test
    public void deveRetornarEmpregado206_QuandoConsultarEmpregado(){

        setUp();

        RestAssured.given()
                .basePath("/hr/employees")
                .pathParam("employeeId",206)
                .accept(ContentType.JSON)
            .when()
                .get("/{employeeId}")
            .then()
                .statusCode(HttpStatus.OK.value())
                .body("firstName", Matchers.equalTo("William"));

    }

}
