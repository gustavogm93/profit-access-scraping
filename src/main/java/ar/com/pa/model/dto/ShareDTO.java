package ar.com.pa.model.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ar.com.pa.model.props.Share;
import lombok.Data;

@Data
@Document(collection = "Share")
public class ShareDTO {

	@Id
	private String id;
	
	@Field(name = "properties")
	private Share properties;
	

	public ShareDTO(Share share) {
		this.properties = share;
	}
	
	

	
}
