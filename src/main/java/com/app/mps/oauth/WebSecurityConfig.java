package com.app.mps.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder userPasswordEncoder;

	/*
	 * @AutowiredF private BCryptPasswordEncoder passwordEncoder;
	 */

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication().withUser("john").password(userPasswordEncoder.encode("123")).roles("USER").and()
				.withUser("tom").password(userPasswordEncoder.encode("111")).roles("ADMIN").and().withUser("user1")
				.password(userPasswordEncoder.encode("pass")).roles("USER").and().withUser("admin")
				.password(userPasswordEncoder.encode("nimda")).roles("ADMIN");

		auth.userDetailsService(userDetailsService).passwordEncoder(userPasswordEncoder);
	}// @formatter:on

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests().antMatchers("/login").permitAll().antMatchers("/oauth/token/revokeById/**").permitAll()
				.antMatchers("/tokens/**").permitAll().anyRequest().authenticated().and().formLogin().permitAll().and()
				.csrf().disable();
		// @formatter:on
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/console/**").antMatchers("/swagger/**");
	}

}
