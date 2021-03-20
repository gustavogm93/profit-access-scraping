package ar.com.pa.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Document(collection = "Region")
@Component
public class RegionDTO {

	Region region;
}
