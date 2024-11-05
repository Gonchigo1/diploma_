package mn.astvision.starter.repository.sequence;

import mn.astvision.starter.model.sequence.SequenceNumber;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceNumberRepository extends MongoRepository<SequenceNumber, String> {

}
