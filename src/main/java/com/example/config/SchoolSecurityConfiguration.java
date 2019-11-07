package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
// @EnableWebSecurity
public class SchoolSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    DataSource dataSource;

	//Enable jdbc authentication
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        //auth.jdbcAuthentication().dataSource(dataSource);
    	auth.inMemoryAuthentication().withUser("eric")
    								 .password("{noop}123456")
								 	 .roles("USER");
    }

	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/home").hasAnyRole("USER", "ADMIN")
//				.antMatchers("/students").hasAnyRole("USER", "ADMIN").antMatchers("/modules")
//				.hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
//				.and().logout().permitAll();
//
//		http.csrf().disable();
		
		http.authorizeRequests()
			.antMatchers("/").permitAll()
		    .anyRequest().authenticated()
		    .and()
		    .formLogin().permitAll()
		    .defaultSuccessUrl("/", true)
		    .and().logout().permitAll();

		http.csrf().disable();
	}
	
}