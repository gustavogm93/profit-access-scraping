package ar.com.pa;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"ar.com.pa.collections","ar.com.pa.controller", "ar.com.pa.utils", "ar.com.pa.scraping", "ar.com.pa.enums"})
public class Application extends SpringBootServletInitializer {
	
    public static void main(String[] args) {
    	SpringApplication.run( Application.class, args);
    }
}
