package com.graphql.demo.controller;

import com.graphql.demo.entity.Department;
import com.graphql.demo.entity.Employee;
import com.graphql.demo.exception.EmployeeNotFoundException;
import com.graphql.demo.repository.DepartmentRepository;
import com.graphql.demo.repository.EmployeeRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    public EmployeeController(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @QueryMapping
    public List<Employee> employees() {
        return employeeRepository.findAll();
    }

    @QueryMapping
    public List<Employee> employeesByDepartment(@Argument Long departmentId) {
        return employeeRepository.findByDepartment_Id(departmentId);
    }

    @QueryMapping
    public List<Department> departments() {
        return departmentRepository.findAll();
    }

    @SchemaMapping(typeName = "Department", field = "employees")
    public List<Employee> departmentEmployees(Department department) {
        return employeeRepository.findByDepartment_Id(department.getId());
    }

    @QueryMapping
    public Employee employeeById(@Argument Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }


    @MutationMapping
    public Employee addEmployee(
            @Argument String name,
            @Argument Long departmentId,
            @Argument Double salary) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found with id: " + departmentId));

        Employee employee = new Employee(name, department, salary);

        return employeeRepository.save(employee);
    }


    @MutationMapping
    public Department addDepartment(@Argument String name) {
        Department department = new Department(name);
        return departmentRepository.save(department);
    }


    @MutationMapping
    public Employee updateEmployee(
            @Argument Long id,
            @Argument String name,
            @Argument Long departmentId,
            @Argument Double salary) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(id));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Department not found with id: " + departmentId));

        employee.setName(name);
        employee.setDepartment(department);
        employee.setSalary(salary);

        return employeeRepository.save(employee);
    }


    @MutationMapping
    public Boolean deleteEmployee(@Argument Long id) {

        if (!employeeRepository.existsById(id)) {
            return false;
        }

        employeeRepository.deleteById(id);
        return true;
    }
}