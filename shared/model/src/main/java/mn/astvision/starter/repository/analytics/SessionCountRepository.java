package mn.astvision.starter.repository.analytics;

import mn.astvision.starter.model.analytics.SessionCount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

/**
 * @author digz6666
 */
@Repository
public interface SessionCountRepository extends MongoRepository<SessionCount, LocalDate> {

    SessionCount findTop1ByOrderByDateDesc();

    SessionCount findTop1ByDate(LocalDate date);
}
