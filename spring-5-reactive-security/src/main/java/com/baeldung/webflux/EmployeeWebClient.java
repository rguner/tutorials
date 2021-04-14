package com.baeldung.webflux;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class EmployeeWebClient {

    WebClient client = WebClient.create("http://localhost:8080");

    public void consume() {

        Mono<Employee> employeeMono = client.get()
                .uri("/employees/{id}", "1")
                .retrieve()
                .bodyToMono(Employee.class);

        employeeMono.subscribe(System.out::println);

        Flux<Employee> employeeFlux = client.get()
                .uri("/employees")
                .retrieve()
                .bodyToFlux(Employee.class);

        employeeFlux.subscribe(System.out::println);

        //int count=100;
        int count=1;
        for (int i=0;i<count;i++) {
            int finalI1 = i;
            CompletableFuture.supplyAsync(() -> {
                Flux<Employee> employeeFlux1 = client.get()
                        .uri("/employees//all/{id}", "_t" + String.valueOf(finalI1) + "t")
                        .retrieve()
                        .bodyToFlux(Employee.class);

                //employeeFlux1.subscribe(System.out::println);
                int finalI = finalI1;
                employeeFlux1.subscribe(f -> System.out.println("Bitti : " + "_" + finalI));
                return "selam";
            });
        }
    }

}