package mn.astvision.starter.repository.oxfordThinkers;

import mn.astvision.starter.model.oxfordThinkers.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class OxfordThinkersCustomRepositoryImpl implements OxfordThinkersCustomRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Topic> list(String role,
                            String search,
                            Boolean using2fa,
                            Boolean emailVerified,
                            Boolean phoneVerified,
                            Boolean active,
                            Boolean deleted,
                            Pageable pageable) {
        Query query = new Query();

        // Add your filtering logic based on the parameters
        if (role != null) {
            query.addCriteria(Criteria.where("role").is(role));
        }
        if (search != null) {
            query.addCriteria(Criteria.where("name").regex(search, "i")); // Example search on name
        }
        if (using2fa != null) {
            query.addCriteria(Criteria.where("using2fa").is(using2fa));
        }
        if (emailVerified != null) {
            query.addCriteria(Criteria.where("emailVerified").is(emailVerified));
        }
        if (phoneVerified != null) {
            query.addCriteria(Criteria.where("phoneVerified").is(phoneVerified));
        }
        if (active != null) {
            query.addCriteria(Criteria.where("active").is(active));
        }
        if (deleted != null) {
            query.addCriteria(Criteria.where("deleted").is(deleted));
        }

        query.with(pageable);
        return mongoTemplate.find(query, Topic.class);
    }
}
