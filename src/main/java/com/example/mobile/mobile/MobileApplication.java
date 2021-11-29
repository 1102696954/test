package com.example.mobile.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableOpenApi
@EnableSwagger2
@SpringBootApplication
public class MobileApplication {

  public static void main(String[] args) {
    SpringApplication.run(MobileApplication.class, args);
  }

}
