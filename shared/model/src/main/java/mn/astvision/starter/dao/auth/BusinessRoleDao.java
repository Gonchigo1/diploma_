package mn.astvision.starter.dao.auth;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.auth.BusinessRole;
import mn.astvision.starter.model.auth.enums.ApplicationRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * @author Tergel
 */
@Repository
@RequiredArgsConstructor
public class BusinessRoleDao {

    private final MongoTemplate mongoTemplate;

    public long count(String role, ApplicationRole applicationRole) {
        return mongoTemplate.count(buildPredicate(role, applicationRole, null), BusinessRole.class);
    }

    public Iterable<BusinessRole> list(String role, ApplicationRole applicationRole, Pageable pageable) {
        return mongoTemplate.find(buildPredicate(role, applicationRole, pageable), BusinessRole.class);
    }

    private Query buildPredicate(String role, ApplicationRole applicationRole, Pageable pageable) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(role))
            query.addCriteria(Criteria.where("role").is(role));

        if (applicationRole != null)
            query.addCriteria(Criteria.where("applicationRoles").is(applicationRole));

        if (pageable != null)
            query.with(pageable);

        return query;
    }
}
