package com.app.mps.oauth;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.DelegatingJwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.IssuerClaimVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtClaimsSetVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/**
	 * This method now makes use of basic auth with the client credentials stored
	 * for authentication Pattern Basci base64 of client credentials (Basic
	 * base64(username:password))
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.anyRequest().authenticated().and().httpBasic().realmName("TEST_REALM")
				.authenticationEntryPoint(basicAuth()).and().authorizeRequests().antMatchers("/employee")
				.authenticated();

	}

	@Bean
	public AuthenticationEntryPoint basicAuth() {
		BasicAuthenticationEntryPoint b = new BasicAuthenticationEntryPoint();
		b.setRealmName("TEST_REALM");
		return b;
	}

	// JWT token store

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		converter.setJwtClaimsSetVerifier(jwtClaimsSetVerifier());
		return converter;
	}

	@Bean
	public JwtClaimsSetVerifier jwtClaimsSetVerifier() {
		return new DelegatingJwtClaimsSetVerifier(Arrays.asList(issuerClaimVerifier()));
	}

	@Bean
	public JwtClaimsSetVerifier issuerClaimVerifier() {
		try {
			// TODO to put this to application yml file and then refactor commneted code.
			return new IssuerClaimVerifier(new URL("http://localhost:8081"));
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * @Bean public JwtClaimsSetVerifier customJwtClaimVerifier() { return new
	 * CustomClaimVerifier(); }
	 */

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}
}
