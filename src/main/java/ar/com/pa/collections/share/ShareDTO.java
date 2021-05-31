package ar.com.pa.collections.share;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import ar.com.pa.collections.country.CountryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class ShareDTO {

	@Id
	private String id;
	
	@Field(name = "properties")
	@NonNull private ShareProp properties;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		ShareDTO other = (ShareDTO) obj;

		if (!other.id.equalsIgnoreCase(this.id))
			return false;

		if (!other.properties.getCode().equalsIgnoreCase(this.properties.getCode()))
			return false;
		
		return true;
	}
}
