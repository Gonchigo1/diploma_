package mn.astvision.starter.dao.auth;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.auth.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Tergel
 */
@Repository
@RequiredArgsConstructor
public class UserDao {

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public long count(
            String role,
            String search,
            Boolean using2fa,
            Boolean emailVerified,
            Boolean phoneVerified,
            Boolean active
             ) {
        return mongoTemplate.count(buildPredicate(
                role,
                search,
                using2fa,
                emailVerified,
                phoneVerified,
                active,
                null), User.class);
    }

    public List<User> list(
            String role,
            String search,
            Boolean using2fa,
            Boolean emailVerified,
            Boolean phoneVerified,
            Boolean active,
            Pageable pageable) {
        return mongoTemplate.find(buildPredicate(
                role,
                search,
                using2fa,
                emailVerified,
                phoneVerified,
                active,
                pageable), User.class);
    }

    private Query buildPredicate(
            String role,
            String search,
            Boolean using2fa,
            Boolean emailVerified,
            Boolean phoneVerified,
            Boolean active,
            Pageable pageable) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(role))
            query.addCriteria(Criteria.where("role").is(role));

        if (active != null)
            query.addCriteria(Criteria.where("active").is(active));

        if (using2fa != null)
            query.addCriteria(Criteria.where("using2fa").is(using2fa));

        if (emailVerified != null)
            query.addCriteria(Criteria.where("emailVerified").is(emailVerified));

        if (phoneVerified != null)
            query.addCriteria(Criteria.where("phoneVerified").is(phoneVerified));

            query.addCriteria(Criteria.where("deleted").is(false));

        if (!ObjectUtils.isEmpty(search)) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("email").regex(search, "i"),
                    Criteria.where("registryNumber").regex(search, "i"),
                    Criteria.where("lastName").regex(search, "i"),
                    Criteria.where("firstName").regex(search, "i"),
                    Criteria.where("mobile").regex(search, "i")
            ));
        }

        if (pageable != null)
            query.with(pageable);

        return query;
    }

    public Map<String, User> map(List<String> ids) {
        Map<String, User> userMap = new HashMap<>();

        List<User> users = userRepository.findAllByIdIn(ids);
        for (User user : users) {
            userMap.put(user.getId(), user);
        }

        return userMap;
    }

    public List<String> listIds(String search) {
        List<String> userIds = new ArrayList<>();
        if (ObjectUtils.isEmpty(search))
            return userIds;

        Query query = new Query();
        query.addCriteria(where("deleted").is(false));
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("email").regex(search, "i"),
                Criteria.where("registryNumber").regex(search, "i"),
                Criteria.where("lastName").regex(search, "i"),
                Criteria.where("firstName").regex(search, "i"),
                Criteria.where("mobile").regex(search, "i")
        ));
        query.fields().include("id");

        List<User> users = mongoTemplate.find(query, User.class);
        for (User user : users)
            userIds.add(user.getId());

        return userIds;
    }

    public User getInternalApi() {
        List<User> users = userRepository.findInternalApi(PageRequest.of(0, 1));
        return users.size() > 0 ? users.get(0) : null;
    }
}
