package com.curso.springboot.jpa.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


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
}
