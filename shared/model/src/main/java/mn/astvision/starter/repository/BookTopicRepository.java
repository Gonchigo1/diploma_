package mn.astvision.starter.repository;
import mn.astvision.starter.model.BookTopic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTopicRepository extends MongoRepository<BookTopic, String>{
}
