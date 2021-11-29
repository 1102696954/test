package com.example.mobile.mobile.commons.configuration;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName Sawgger3Config
 * @Description: 第一步创建swagger配置类  http://localhost:端口号/swagger-ui.html#!
 * @Author ts-lingcai.kong
 * @Date 2021/11/29 9:24
 */
@Configuration
public class Swagger3Config {

  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.OAS_30)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
        .paths(PathSelectors.any())
        .build();
  }

  //定义api的信息
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Swagger3 API Document")
        .description("test")
        .contact(new Contact("lingcai", "", "1102696954@qq.com"))
        .version("1.0.0").build();
  }
}
