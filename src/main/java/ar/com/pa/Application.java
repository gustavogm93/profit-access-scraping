package ar.com.pa;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.springframework.boot.*;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
public class Application {
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

}
