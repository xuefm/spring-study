package com.expect.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
class Boot3ApplicationTests {
    @Autowired
    private Environment environment;


    @Test
    void contextLoads() {
        System.out.println(environment.getActiveProfiles());
    }

}
