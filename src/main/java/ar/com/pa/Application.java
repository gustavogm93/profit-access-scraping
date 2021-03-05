package ar.com.pa;

import org.springframework.boot.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("ar.com.pa.services")
public class Application {
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

}
