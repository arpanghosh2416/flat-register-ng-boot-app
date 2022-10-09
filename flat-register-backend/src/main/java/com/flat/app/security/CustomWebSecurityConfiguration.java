package com.flat.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.flat.app.jwt.JwtAuthenticationEntryPoint;
import com.flat.app.jwt.JwtAuthenticationFilter;
import com.flat.app.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// ################## Permit All ##################
	private static final String FLAT_ROOT = "/api/flat";
	private static final String USER_ROOT = "/api/user";
	private static final String OWNER_ROOT = "/api/owner";

	private static final String H2 = "/h2db/**";
	private static final String AUTH = "/api/user/auth/**";
	private static final String FEEDBACK = "/api/feedback/**";
	private static final String REGISTER = "/api/user/register";
	private static final String FLAT_LIST = "/api/flat/get-all-flats";
	// ################## Permit All ##################
	
	// ################## ADMIN ##################
	private static final String USER_LIST = "/api/user/get-all-users";
	// ################## ADMIN ##################

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		super.configure(http);
		http
			.csrf().ignoringAntMatchers(H2)
			.and()
			.headers().frameOptions().sameOrigin();
		http
			.cors().and()
			.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpHeaders.ALLOW).permitAll()
			.antMatchers(USER_LIST).hasRole("ADMIN")
			.antMatchers("/", FLAT_ROOT, USER_ROOT, OWNER_ROOT).permitAll()
			.antMatchers(H2, AUTH, FEEDBACK, REGISTER, FLAT_LIST).permitAll()
			.anyRequest().authenticated();
		http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		super.configure(auth);
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(10);
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
