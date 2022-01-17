package br.com.alura.forum.config.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.alura.forum.model.User;
import lombok.NoArgsConstructor;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
@NoArgsConstructor
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfiguration {

	@Bean
	public Docket forumApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
				.paths(PathSelectors.ant("/**"))
				.build()
				.ignoredParameterTypes(User.class)
				.globalRequestParameters(globalParams());
	}

	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Fórum API")
				.description("API do curso simulando fórum da Alura")
				.version("0.1.0-SNAPSHOT")
				.build();
	}

	@Bean
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
				.deepLinking(Boolean.TRUE)
				.displayOperationId(false)
				.filter(false)
				.operationsSorter(OperationsSorter.ALPHA)
				.tagsSorter(TagsSorter.ALPHA)
				.build();
	}

	private List<RequestParameter> globalParams() {
		RequestParameterBuilder paramBuilder = new RequestParameterBuilder();
		RequestParameter authParam = paramBuilder.name("Authorization")
			.description("Header para o token JWT")
			
			.in(ParameterType.HEADER)
			.required(Boolean.FALSE)
			.build();
		return List.of(authParam);
	}
}