package net.croz.pancakes_unlimited;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@SpringBootApplication
public class PancakesUnlimitedApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(PancakesUnlimitedApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper()
    {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        return mapper;
    }

    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo()
    {
        return new ApiInfo("Pancakes unlimited", "let's bake some pancakes", "1.0.0", "", null, "", "", new ArrayList<>());
    }
}
