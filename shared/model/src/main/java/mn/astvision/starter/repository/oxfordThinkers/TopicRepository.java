package mn.astvision.starter.repository.oxfordThinkers;

import mn.astvision.starter.model.oxfordThinkers.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {
    @Nullable
    Topic findByIdAndDeletedFalse(String code);

    int countByBookIdAndDeletedFalse(String Id);

}

