package mn.astvision.starter.repository.sso;

import mn.astvision.starter.model.sso.SsoRequestLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SsoRequestLogRepository extends MongoRepository<SsoRequestLog, String> {
}
