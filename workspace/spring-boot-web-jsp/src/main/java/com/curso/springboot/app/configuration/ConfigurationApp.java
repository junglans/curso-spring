package com.curso.springboot.app.configuration;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ConfigurationApp {
	 
	@Bean
	public PropertyPlaceholderConfigurer applicationProperties() {
		PropertyPlaceholderConfigurer res = new PropertyPlaceholderConfigurer();
	    res.setFileEncoding("UTF-8");
	    res.setLocation(new ClassPathResource("application.properties"));
	    return res;
	}
}
