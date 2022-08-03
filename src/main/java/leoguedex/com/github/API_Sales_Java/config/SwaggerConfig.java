package leoguedex.com.github.API_Sales_Java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(
                DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(
                        RequestHandlerSelectors.basePackage("leoguedex.com.github.API_Sales_Java.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(DefaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> DefaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "acessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        SecurityReference reference = new SecurityReference("JWT", scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Sales Java")
                .description("API for Sales in Java")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact())
                .build();
    }

    private Contact contact (){
        return new Contact("Leonardo C. Guedes",
                "https://github.com/LeoGuedex",
                "leocguedex@gmail.com");
    }
}
