package org.toasthub.hive.configuration;

import org.toasthub.hive.controller.RestAuthenticationEntryPoint;
import org.toasthub.hive.security.api.UserManagerSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class MultiHttpSecurityConfig {
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
    private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	UserManagerSvc userManagerSvc;
		
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(4);
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable()
		.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).accessDeniedHandler(accessDeniedHandler)
		.and().httpBasic()
		.and()
		.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/api/public/**", "/", "/login/**", "/css/**", "/img/**", "/libs/**", "/js/**", "/public/**", "/dist/**" ).permitAll()
	            .anyRequest().authenticated()
				)
		.securityContext((securityContext) -> securityContext.requireExplicitSave(false));
	
	return http.build();
	}
	
}
