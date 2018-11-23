package com.curso.springboot.jpa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * Note that the AuthenticationManagerBuilder is @Autowired into a method in a @Bean - 
	 * that is what makes it build the global (parent) AuthenticationManager.
	 * @param build
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder usersBuilder = User.builder().passwordEncoder(encoder::encode);
		
		build.inMemoryAuthentication()
		.withUser(usersBuilder.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(usersBuilder.username("juan").password("12345").roles("USER"));
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.authorizeRequests()
		 .antMatchers("/", "/css/**", "/js/**", "/images/**", "/clients").permitAll() // rutas publicas
		 .antMatchers("/detail/**").hasAnyRole("USER")
		 .antMatchers("/uploads/**").hasAnyRole("USER")
		 .antMatchers("/form/**").hasAnyRole("ADMIN")
		 .antMatchers("/delete/**").hasAnyRole("ADMIN")
		 .antMatchers("/invoices/**").hasAnyRole("ADMIN")
		 .anyRequest().authenticated()
		 .and()
		 .formLogin().loginPage("/login").permitAll()
		 .and()
		 .logout().permitAll();
		 
	}
}
