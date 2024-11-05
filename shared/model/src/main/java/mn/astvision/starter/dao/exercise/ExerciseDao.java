package mn.astvision.starter.dao.exercise;
import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.exercise.Exercise;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Repository
public class ExerciseDao {

    private final MongoTemplate mongoTemplate;

    public long count(
            String bookId,
            String topicId,
            String lessonId,
            String exercise

            ) {
        return mongoTemplate.count(buildQuery(
                bookId,
                topicId,
                lessonId,
                exercise
                ), Exercise.class);
    }

    public Iterable<Exercise> list(
            String bookId,
            String topicId,
            String lessonId,
            String exercise,
            Pageable pageable
    ) {
        Query query = buildQuery(
                bookId,
                topicId,
                lessonId,
                exercise
        );
        if (pageable != null) {
            query = query.with(pageable);
        }
        return mongoTemplate.find(query, Exercise.class);
    }

    private Query buildQuery(String bookId, String topicId, String lessonId,String exercise) {
        Query query = new Query();


        if (!ObjectUtils.isEmpty(bookId))
            query.addCriteria(Criteria.where("bookId").is(bookId));

        if (!ObjectUtils.isEmpty(lessonId))
            query.addCriteria(Criteria.where("lessonId").is(lessonId));

        if (!ObjectUtils.isEmpty(topicId))
            query.addCriteria(Criteria.where("topicId").is(topicId));

        if (!ObjectUtils.isEmpty(exercise))
            query.addCriteria(Criteria.where("exercise").is(exercise));

        query.addCriteria(Criteria.where("deleted").is(false));

        return query;
    }
}

