package ar.com.pa.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.constructor.Construct;

import ar.com.pa.repository.RegionRepository;
import io.vavr.control.Try;

@Service
public class SerializeImpl {

	private RegionRepository regionRepository;
	
	@Value("${enum.path}")
	private String enumPath;
	
	private final Logger logger = LoggerFactory.getLogger(SerializeImpl.class);
	
	public void save(Set<String> obj, String path){

		logger.info(String.format("Save .txt in:%s ", path));
		
		File enumPath = new File(getEnumPath() + path + ".txt");
	
		Try<PrintWriter> pw = Try.of(() -> new PrintWriter(new OutputStreamWriter(new FileOutputStream(enumPath), "UTF-8")));
		
		pw.onSuccess(data -> {

			obj.stream().forEach(data::println);

		});
		
		pw.onFailure(ex -> {
			logger.error(ex.toString());
		});
		
	}

	
	public void print(Set<String> obj) {
		
		obj.stream().forEach(System.out::println);
	}
	
	
		public String getEnumPath(){
		return this.enumPath;
	}
		
		@Autowired
		public SerializeImpl() {}
}
