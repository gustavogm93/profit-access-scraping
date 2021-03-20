package ar.com.pa.model;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(collection = "Region")
@Component
public class RegionDTO {

	@MongoId(targetType = FieldType.STRING)
	private String id;
	private String title;
	private List<Country> countries = new ArrayList<>();
}
