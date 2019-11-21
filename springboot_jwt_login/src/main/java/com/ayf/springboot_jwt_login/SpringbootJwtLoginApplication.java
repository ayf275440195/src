package com.ayf.springboot_jwt_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class SpringbootJwtLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJwtLoginApplication.class, args);
    }

}
