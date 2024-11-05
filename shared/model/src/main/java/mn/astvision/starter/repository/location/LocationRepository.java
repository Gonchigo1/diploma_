package mn.astvision.starter.repository.location;

import mn.astvision.starter.model.location.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author digz6666
 */
@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
}
