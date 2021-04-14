package com.baeldung.webflux;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Repository
public class EmployeeRepository {
    
    static Map<String,Employee> employeeData;

    static Map<String,String> employeeAccessData;

    static
    {
        employeeData = new HashMap<>();
        employeeData.put("1",new Employee("1","Employee 1"));
        employeeData.put("2",new Employee("2","Employee 2"));
        employeeData.put("3",new Employee("3","Employee 3"));
        employeeData.put("4",new Employee("4","Employee 4"));
        employeeData.put("5",new Employee("5","Employee 5"));
        employeeData.put("6",new Employee("6","Employee 6"));
        employeeData.put("7",new Employee("7","Employee 7"));
        employeeData.put("8",new Employee("8","Employee 8"));
        employeeData.put("9",new Employee("9","Employee 9"));
        employeeData.put("10",new Employee("10","Employee 10"));
        
        employeeAccessData=new HashMap<>();
        employeeAccessData.put("1", "Employee 1 Access Key");
        employeeAccessData.put("2", "Employee 2 Access Key");
        employeeAccessData.put("3", "Employee 3 Access Key");
        employeeAccessData.put("4", "Employee 4 Access Key");
        employeeAccessData.put("5", "Employee 5 Access Key");
        employeeAccessData.put("6", "Employee 6 Access Key");
        employeeAccessData.put("7", "Employee 7 Access Key");
        employeeAccessData.put("8", "Employee 8 Access Key");
        employeeAccessData.put("9", "Employee 9 Access Key");
        employeeAccessData.put("10", "Employee 10 Access Key");
    }
    
    public Mono<Employee> findEmployeeById(String id)
    {
        return Mono.just(employeeData.get(id));
    }
    
    public Flux<Employee> findAllEmployees()
    {
        System.out.println("EmployeeRepository.findAllEmployees :" + Thread.currentThread().getName());
        //Flux<Employee> flux = getData();
        //Flux<Employee> flux = getDataFromParallel();
        Flux<Employee> flux = getDataFromRemoteWithParallel();
        System.out.println("EmployeeRepository.flux             :" + Thread.currentThread().getName());
        return flux;
    }

    private Flux<Employee> getData() {
        Flux<Employee> flux = Flux.fromIterable(getEmployees());
        return flux;
    }

    private Flux<Employee> getDataFromSameThread() {
        Flux<Employee> flux = Flux.fromIterable(getEmployees())
                .log();
        return flux;
    }


    private Flux<Employee> getDataFromParallelWithDuration() {
        Flux<Employee> flux = Flux.fromIterable(getEmployees())
                .log()
                .delaySubscription(Duration.ofMillis(5000));
        return flux;
    }

    private Flux<Employee> getDataFromParallel() {
        Flux<Employee> flux = Flux.fromIterable(getEmployees())
                .log()
                .subscribeOn(Schedulers.newParallel("thread-parallel"))
                ;
        // subscribeOn(Schedulers.newParallel("thread-parallel")) aktifken paralel işler her iki subscriber'ı
        //subscribeOn aktif değils, subscriber'lar ser işlenir, biri biter diğeri başlar.
        flux.subscribe(s->System.out.println("EMPLOYEE: ____ " + s.getName()));
        flux.subscribe(s->System.out.println("EMPLOYEE2: ::::: " + s.getName()));

        return flux;
    }

    private Flux<Employee> getDataFromRemoteWithParallel() {
        Flux<Employee> flux = Flux.fromIterable(getEmployees())
                .log()
                .subscribeOn(Schedulers.newParallel("thread-parallel"))
                ;
        // subscribeOn(Schedulers.newParallel("thread-parallel")) aktifken paralel işler her iki subscriber'ı
        //subscribeOn aktif değils, subscriber'lar ser işlenir, biri biter diğeri başlar.
        flux.subscribe(s->System.out.println("EMPLOYEE: ____ " + s.getName()));
        flux.subscribe(s->System.out.println("EMPLOYEE2: ::::: " + s.getName()));

        return flux;
    }

    public Flux<Employee> findAllEmployees(String trId)
    {
        System.out.println("EmployeeRepository.findAllEmployees :" + Thread.currentThread().getName() + " " + trId);
        Flux<Employee> flux = Flux.fromIterable(getEmployees(trId));
        System.out.println("EmployeeRepository.flux             :" + Thread.currentThread().getName() + " " + trId);
        return flux;
    }

    private Collection<Employee> getEmployees(String trId) {
        System.out.println("EmployeeRepository.getEmployees :" + Thread.currentThread().getName() + " " + trId);
        sleep();
        Collection<Employee> employees = employeeData.values();
        return employees;
    }


    private Collection<Employee> getEmployees() {
        System.out.println("EmployeeRepository.getEmployees :" + Thread.currentThread().getName());
        sleep();
        Collection<Employee> employees = employeeData.values();
        return employees;
    }

    public Mono<Employee> updateEmployee(Employee employee)
    {
        Employee existingEmployee=employeeData.get(employee.getId());
        if(existingEmployee!=null)
        {
            existingEmployee.setName(employee.getName());
        }
        return Mono.just(existingEmployee);
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
