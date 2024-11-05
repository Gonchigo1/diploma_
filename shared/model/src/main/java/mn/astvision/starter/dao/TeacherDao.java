package mn.astvision.starter.dao;
import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.Teacher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Repository
public class TeacherDao {

    private final MongoTemplate mongoTemplate;

    public long count( String school,
                       String teacherLName,
                       String name,
                       String phone,
                       String email,
                       String userName,
                       String Password,
                       Boolean active
    ) {
        return mongoTemplate.count(buildQuery(
                school,
                teacherLName,
                name,
                phone,
                email,
                userName,
                Password,
                active
        ), Teacher.class);
    }

    public Iterable<Teacher> list(String school,
                                  String teacherLName,
                                  String name,
                                  String phone,
                                  String email,
                                  String userName,
                                  String Password,
                                  Boolean active,
                                  Pageable pageable) {
        Query query = buildQuery(
                school,
                teacherLName,
                name,
                phone,
                email,
                userName,
                Password,
                active
        );
        if (pageable != null) {
            query = query.with(pageable);
        }
        return mongoTemplate.find(query, Teacher.class);
    }

    private Query buildQuery(String school,
                             String teacherLName,
                             String name,
                             String phone,
                             String email,
                             String userName,
                             String Password,
                             Boolean active) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(name))
            query.addCriteria(Criteria.where("teacherFirstname").regex(name, "i"));

        if (!ObjectUtils.isEmpty(school))
            query.addCriteria(Criteria.where("school").is(school));

        if (!ObjectUtils.isEmpty(teacherLName))
            query.addCriteria(Criteria.where("teacherLastName").is(teacherLName));

        if (!ObjectUtils.isEmpty(phone))
            query.addCriteria(Criteria.where("teacherPhone").is(phone));

        if (!ObjectUtils.isEmpty(email))
            query.addCriteria(Criteria.where("teacherEmail").is(email));

        if (!ObjectUtils.isEmpty(userName))
            query.addCriteria(Criteria.where("teacherLoginName").is(userName));

        if (!ObjectUtils.isEmpty(Password))
            query.addCriteria(Criteria.where("password").is(Password));

        if (!ObjectUtils.isEmpty(active))
            query.addCriteria(Criteria.where("active").is(active));

        query.addCriteria(Criteria.where("deleted").is(false));

        return query;
    }

}

