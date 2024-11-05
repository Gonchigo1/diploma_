package mn.astvision.starter.dao.oxfordThinkers;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.oxfordThinkers.Topic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class OxfordThinkersDao {
    private final MongoTemplate mongoTemplate;

    public long count(
            String bookId,
            String name
    ) {
        return mongoTemplate.count(
                buildQuery(
                        bookId,
                        name
                ), Topic.class);
    }

    public Iterable<Topic> list(
            String bookId,
            String name,
            PageRequest request
    ) {
        Query query = buildQuery(bookId, name);
        query = query.with(Objects.requireNonNullElseGet(request, () -> PageRequest.of(0, 10, Sort.Direction.DESC, "id")));

        return mongoTemplate.find(query, Topic.class);
    }

    private Query buildQuery(String bookId, String name) {
        Query query = new Query();

        if (bookId != null)
            query.addCriteria(Criteria.where("bookId").is(bookId));

        if (name != null)
            query.addCriteria(Criteria.where("name").regex(name, "i"));

        query.addCriteria(Criteria.where("deleted").is(false));
        return query;
    }

}
