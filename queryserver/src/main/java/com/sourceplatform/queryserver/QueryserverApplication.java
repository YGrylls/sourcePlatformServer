package com.sourceplatform.queryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sourceplatform.server.*"})
public class QueryserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryserverApplication.class, args);
    }

}
