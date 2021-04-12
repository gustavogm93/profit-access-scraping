package ar.com.pa.share;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends MongoRepository<ShareDTO, String>{
		@Override
		List<ShareDTO> findAll();

}
//TODO GENERIC MONGO REPOSITORY