package com.curso.springboot.jpa.configuration;


import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.curso.springboot.jpa.view.xml.ClientList;


@Configuration
public class MvcConfig implements WebMvcConfigurer {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Con esto añadimos carpetas externas como si fueran recursos internos, como
		// si estuviera en la carpeta "resources".
//		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//		super.addResourceHandlers(registry);
//		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**************************************
	 * 
	 * Beans para la internacionalización.
	 * 
	 * ***********************************/
	@Bean
	/**
	 * Para que la aplicación sea capaz de resolver el locale que se está utilizando es necesario 
	 * registrar un LocaleResolver
	 * @return
	 */
	public LocaleResolver localeResolver() {
		// El locale se guardará enla sesión http.
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es","ES"));
		return localeResolver;
	}
	
	@Bean
	/**
	 * Este interceptor se ejecuta en cada petición, en cada request, y modifica el locale basado en el valor de
	 * el parámetro lang.
	 * @return
	 */
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	/**
	 * Registramos el interceptor.
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor( localeChangeInterceptor() );
	}
	
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller(){
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] {ClientList.class});
		return marshaller;
	}
}