package sk.jarina.cvut.uloha6;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Uloha6Application {

	public static void main(String[] args) {
		SpringApplication.run(Uloha6Application.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Uloha 7")
						.version("1.0")
						.description("Rest Application")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));

	}

}
