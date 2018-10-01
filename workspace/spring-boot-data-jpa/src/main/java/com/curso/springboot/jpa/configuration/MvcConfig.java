package com.curso.springboot.jpa.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Value("${application.resources.path}")
	private String uploadPath;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Con esto añadimos carpetas externas como si fueran recursos internos, como
		// si estuviera en la carpeta "resources".
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + uploadPath);
	}

}
