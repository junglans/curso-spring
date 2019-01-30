package com.curso.springboot.jpa.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.curso.springboot.jpa.auth.handlers.LoginSuccessHandler;
import com.curso.springboot.jpa.services.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	
	@Autowired
	//private DataSource dataSource;
	private JpaUserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Note that the AuthenticationManagerBuilder is @Autowired into a method in a @Bean - 
	 * that is what makes it build the global (parent) AuthenticationManager.
	 * @param build
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
	
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		/*
		 * Esta es la configuración de una autentificación usando jdbc.
 		build.jdbcAuthentication()
 		.dataSource(dataSource)
 		.passwordEncoder(passwordEncoder)
 		.usersByUsernameQuery("SELECT username, password, enabled FROM USERS WHERE username=?")
 		.authoritiesByUsernameQuery("SELECT U.username, R.authority FROM USERS U INNER JOIN USER_ROLE UR ON (UR.user_id = u.id) INNER JOIN ROLES R ON (UR.role_id = r.id) WHERE u.username = ?");
		*/
		
		/*	Esta es la configuración para una autentificación en memoria
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder usersBuilder = User.builder().passwordEncoder(encoder::encode);
		
		build.inMemoryAuthentication()
		.withUser(usersBuilder.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(usersBuilder.username("juan").password("12345").roles("USER"));
		*/
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		 http.authorizeRequests()
		 .antMatchers("/", "/css/**", "/js/**", "/images/**").permitAll() // rutas publicas
		 .antMatchers("/locale").permitAll()
		 .antMatchers("/clients").permitAll()
		 .antMatchers("/api/clients/**").permitAll()
		 /* todas estas autorizaciones se pueden reemplazar con anotaciones.
		 .antMatchers("/detail/**").hasAnyRole("USER")
		 .antMatchers("/uploads/**").hasAnyRole("USER")
		 .antMatchers("/form/**").hasAnyRole("ADMIN")
		 .antMatchers("/delete/**").hasAnyRole("ADMIN")
		 .antMatchers("/clients/**").hasAnyRole("ADMIN", "USER")
		 .antMatchers("/invoices/delete/**").hasAnyRole("ADMIN")
		 */
		 .antMatchers("/invoices/detail/**").permitAll()
		 .anyRequest().authenticated()
		 .and()
		 .formLogin().loginPage("/login").successHandler(loginSuccessHandler).permitAll()
		 .and()
		 .logout().permitAll()
		 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 .logoutSuccessUrl("/login?logout")
		 .invalidateHttpSession(true)
		 .deleteCookies("JSESSIONID")
		 .and().exceptionHandling().accessDeniedPage("/error_403");
		 
	}
}
