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
import java.util.Collections;
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
                .apis(RequestHandlerSelectors.basePackage("leoguedex.com.github.API_Sales_Java.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(new ApiKey("JWT", "Authorization", "header")))
                .apiInfo(apiInfo());
    }

    private SecurityContext securityContext() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        SecurityReference securityReference = SecurityReference.builder()
                .reference("JWT")
                .scopes(new AuthorizationScope[]{authorizationScope})
                .build();
        List<SecurityReference> references = new ArrayList<>();
        references.add(securityReference);

        return SecurityContext.builder()
                .securityReferences(references)
                .forPaths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Sales Java")
                .description("API for Sales in Java")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Leonardo C. Guedes",
                        "https://github.com/LeoGuedex",
                        "leocguedex@gmail.com"))
                .build();
    }

}
