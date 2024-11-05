package mn.astvision.starter.dao.book;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.book.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Repository
public class BookDao {

    private final MongoTemplate mongoTemplate;

    public long count(
            String name,
            String type,
            Boolean deleted
    ) {
        return mongoTemplate.count(buildQuery
                (
                        name,
                        type,
                        deleted),
                Book.class);
    }

    public Iterable<Book> list(
            String name,
            String type,
            Boolean deleted,
            Pageable pageable
    ) {
        Query query = buildQuery(
                name,
                type,
                deleted);
        if (pageable != null) {
            query.with(pageable);
        }
        return mongoTemplate.find(query, Book.class);
    }

    private Query buildQuery(
            String name,
            String type,
            Boolean deleted) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(name)) {
            query.addCriteria(Criteria.where("name").regex(name, "i"));
        }

        if (!ObjectUtils.isEmpty(type)) {
            query.addCriteria(Criteria.where("type").is(type));
        }

        if (deleted != null) {
            query.addCriteria(Criteria.where("deleted").is(deleted));
        } else {
            query.addCriteria(Criteria.where("deleted").is(false));
        }

        return query;
    }
}
