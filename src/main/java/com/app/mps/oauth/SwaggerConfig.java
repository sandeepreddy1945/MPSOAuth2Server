package com.app.mps.oauth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.SwaggerDefinition;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author Sandeep
 *
 */
@EnableSwagger2
@Configuration
@SwaggerDefinition
public class SwaggerConfig {

	@Value("${server.port}")
	private String serverPort;

	@Value("${server.servlet.context-path}")
	private String contextPath;

	public static final String CLIENT_ID = "mpsadminuser";
	public static final String CLIENT_SECRET = "secret";

	@Bean
	public Docket api() {
		/*
		 * List<SecurityScheme> schemes = new ArrayList<>(); // new
		 * ApiKeyAuthDefinition("apiKey", In.HEADER) // new ApiKey("Authorization",
		 * "Authorization", "header") schemes.add(new BasicAuth("base"));
		 * 
		 * return new Docket(DocumentationType.SWAGGER_2) .apiInfo(new
		 * ApiInfoBuilder().description("A Spring Application For MPS Services")
		 * .license("This is Strictly Prohibited For Use by Third Parties.")
		 * .title("A Small Application for holding MPS Services").version("1.0")
		 * .contact(new Contact("Sandeep Reddy", "", "b.sandeepreddy209@gmail.com")) no
		 * format .licenseUrl("") // TODO needs to be updated. .termsOfServiceUrl("") //
		 * TODO needs to be updated. .build())
		 * .securitySchemes(schemes).select().apis(RequestHandlerSelectors.any()).paths(
		 * PathSelectors.any()) .build();
		 */
		SecurityScheme basicAuth = new BasicAuth("base64");

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().securitySchemes(Arrays.asList(/* securityScheme() */basicAuth))
				.securityContexts(Arrays.asList(securityContext()));
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientId(CLIENT_ID).clientSecret(CLIENT_SECRET)
				.useBasicAuthenticationWithAccessCodeGrant(true).build();
	}

	private SecurityScheme securityScheme() {
		GrantType grantType = new AuthorizationCodeGrantBuilder()
				.tokenEndpoint(new TokenEndpoint(authServerURL() + "/token", "oauthtoken"))
				.tokenRequestEndpoint(new TokenRequestEndpoint(authServerURL() + "/authorize", CLIENT_ID, CLIENT_ID))
				.build();

		SecurityScheme oauth = new OAuthBuilder().name("spring_oauth").grantTypes(Arrays.asList(grantType))
				.scopes(Arrays.asList(scopes())).build();
		return oauth;
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes())))
				.forPaths(PathSelectors.regex("/foos.*")).build();
	}

	private AuthorizationScope[] scopes() {
		AuthorizationScope[] scopes = { new AuthorizationScope("read", "for read operations"),
				new AuthorizationScope("write", "for write operations"),
				new AuthorizationScope("admin", "Admin Operations") };
		return scopes;
	}

	private String authServerURL() {
		return "http://localhost:" + serverPort + contextPath + "/oauth";
	}
}
