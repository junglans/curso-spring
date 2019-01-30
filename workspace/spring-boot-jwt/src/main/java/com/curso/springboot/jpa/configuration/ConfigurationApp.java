package com.curso.springboot.jpa.configuration;

import org.dozer.DozerBeanMapper;
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
	
	@Bean
	public DozerBeanMapper dozerBeanMapper() throws Exception {
		DozerBeanMapper mapper = new DozerBeanMapper();
	    return mapper;
    }
}