package mn.astvision.starter.dao.lesson;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.exercise.Exercise;
import mn.astvision.starter.model.lesson.Lesson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
@RequiredArgsConstructor
@Repository
public class LessonDao {

    private final MongoTemplate mongoTemplate;

    public long count(
            String bookId,
            String topicId,
            String lesson

    ) {
        return mongoTemplate.count(buildQuery(
                bookId,
                topicId,
                lesson
        ), Lesson.class);
    }

    public Iterable<Lesson> list(
            String bookId,
            String topicId,
            String lesson,
            Pageable pageable
    ) {
        Query query = buildQuery(
                bookId,
                topicId,
                lesson
        );
        if (pageable != null) {
            query = query.with(pageable);
        }
        return mongoTemplate.find(query, Lesson.class);
    }

    private Query buildQuery(String bookId, String topicId, String lesson) {
        Query query = new Query();


        if (!ObjectUtils.isEmpty(bookId))
            query.addCriteria(Criteria.where("bookId").is(bookId));

        if (!ObjectUtils.isEmpty(topicId))
            query.addCriteria(Criteria.where("topicId").is(topicId));

        if (!ObjectUtils.isEmpty(lesson))
            query.addCriteria(Criteria.where("lessonId").is(lesson));

        query.addCriteria(Criteria.where("deleted").is(false));

        return query;
    }
}

