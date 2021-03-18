package ar.com.pa.utils;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.control.Try;


public class SerializeImpl {

	private static final Logger logger = LoggerFactory.getLogger(SerializeImpl.class);
	
	public static void save(Set<String> obj, String path){

		logger.info(String.format("Save in:%s ", path));
		
		Try<PrintWriter> pw = Try.of(() -> new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8")));
		
		pw.onSuccess(data -> {

			obj.stream().forEach(data::println);

		});
		
		pw.onFailure(ex -> {
			logger.error(ex.toString());
		});
		
	}
}
