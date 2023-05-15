package com.expect.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:expect.properties")
public class SpringStudyApplication {

    @Autowired
    private static Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
        System.out.println();
    }

}
