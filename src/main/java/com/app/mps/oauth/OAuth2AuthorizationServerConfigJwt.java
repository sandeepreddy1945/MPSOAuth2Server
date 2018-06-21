package com.app.mps.oauth;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfigJwt extends AuthorizationServerConfigurerAdapter {

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {

		// clients.jdbc(dataSource).build().loadClientByClientId("mpsadmin");

		clients.inMemory().withClient("mpsguestuser").secret(passwordEncoder().encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read")
				.accessTokenValiditySeconds(3600000).redirectUris("http://localhost:8081/")

				.and().withClient("mpsadminuser").secret(passwordEncoder().encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token")
				.scopes("admin", "read", "write", "update").accessTokenValiditySeconds(3600000)
				// 1 hour
				.refreshTokenValiditySeconds(2592000)
				// 30 days
				.redirectUris("xxx", "http://localhost:8089/")

				.and().withClient("mpssuperuser").secret(passwordEncoder().encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("read", "write")
				.accessTokenValiditySeconds(3600000)
				// 1 hour
				.refreshTokenValiditySeconds(2592000) // 30 days

				.and().withClient("mpstestuser").secret(passwordEncoder().encode("secret"))
				.authorizedGrantTypes("password", "authorization_code", "implicit")
				.scopes("admin", "read", "write", "update").autoApprove(true).redirectUris("xxx");

	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager);
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
