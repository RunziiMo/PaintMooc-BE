package com.runzii.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;

/**
 * Created by runzii on 16-4-20.
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

    @Bean
    public Docket swaggerSpringfoxDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(or(PathSelectors.ant("/api/**"), PathSelectors.ant("/oauth/token")))
                .build();
//        Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .genericModelSubstitutes(ResponseEntity.class)
//                .select()
//                .paths(or(regex("/users.*")
//                        , regex("/courses.*")
//                        , regex("/oauth/token")
//                        , regex("/relations.*"))) // and by paths
//                .build();
//        return swaggerSpringMvcPlugin;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "画吧api文档",
                "本api文档不太科学，最终解释权归Runzii所有",
                "1.0",
                "www.runzii.com",
                "806478101@qq.com",
                null,
                null
        );
    }

}
