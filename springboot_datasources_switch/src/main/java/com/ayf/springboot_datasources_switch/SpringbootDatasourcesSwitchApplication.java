package com.ayf.springboot_datasources_switch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@EnableConfigurationProperties
public class SpringbootDatasourcesSwitchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDatasourcesSwitchApplication.class, args);
    }

}
