package com.curso.springboot.jpa.configuration;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Con esto añadimos carpetas externas como si fueran recursos internos, como
		// si estuviera en la carpeta "resources".
//		String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
//		super.addResourceHandlers(registry);
//		registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
	}

}
