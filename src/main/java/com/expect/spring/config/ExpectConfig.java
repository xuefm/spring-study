package com.expect.spring.config;

import com.expect.spring.entity.User;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ExpectConfig {

    private String a ;
    private String b ;

    @Bean
    public User getUser(){
        return new User();
    }
}
