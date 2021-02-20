package com.bs.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.bs"})
public class DdUpApplication {
    public static void main(String[] args){
        SpringApplication.run(DdUpApplication.class,args);
    }
}
