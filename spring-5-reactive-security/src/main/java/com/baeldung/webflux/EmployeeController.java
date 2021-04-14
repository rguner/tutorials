package com.baeldung.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/{id}")
    private Mono<Employee> getEmployeeById(@PathVariable String id) {
        return employeeRepository.findEmployeeById(id);
    }

    @GetMapping
    private Flux<Employee> getAllEmployees() {
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("EmployeeController.getAllEmployees :" + Thread.currentThread().getName());
        Flux<Employee> f = employeeRepository.findAllEmployees();
        System.out.println("EmployeeController.flux            :" + Thread.currentThread().getName());
        return f;
    }

    @GetMapping("/all/{trId}")
    private Flux<Employee> getAllEmployeesWithTrId(@PathVariable String trId) {
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("EmployeeController.getAllEmployees :" + Thread.currentThread().getName() + " " + trId);
        return employeeRepository.findAllEmployees(trId);
    }

    @PostMapping("/update")
    private Mono<Employee> updateEmployee(@RequestBody Employee employee) {
        return employeeRepository.updateEmployee(employee);
    }

}
