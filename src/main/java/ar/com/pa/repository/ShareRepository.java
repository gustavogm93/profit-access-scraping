package ar.com.pa.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ar.com.pa.model.dto.ShareDTO;

@Repository
public interface ShareRepository extends MongoRepository<ShareDTO, String>{
		@Override
		List<ShareDTO> findAll();

}
//TODO GENERIC MONGO REPOSITORY