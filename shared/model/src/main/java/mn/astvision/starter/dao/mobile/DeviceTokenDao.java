package mn.astvision.starter.dao.mobile;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.mobile.DeviceToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DeviceTokenDao {

    private final MongoTemplate mongoTemplate;

    public long count(
            String os,
            String token,
            String deviceId,
            String email,
            String ip
    ) {
        return mongoTemplate.count(buildQuery(os, token, deviceId, email, ip), DeviceToken.class);
    }

    public List<DeviceToken> list(
            String os,
            String token,
            String deviceId,
            String email,
            String ip,
            PageRequest pageRequest
    ) {
        Query query = buildQuery(os, token, deviceId, email, ip);
        if (pageRequest == null)
            query = query.with(PageRequest.of(0, 10, Sort.Direction.DESC, "id"));
        else
            query = query.with(pageRequest);

        return mongoTemplate.find(query, DeviceToken.class);
    }

    private Query buildQuery(
            String os,
            String token,
            String deviceId,
            String email,
            String ip
    ) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(os))
            query.addCriteria(Criteria.where("os").is(os));

        if (!ObjectUtils.isEmpty(deviceId))
            query.addCriteria(Criteria.where("deviceId").is(deviceId));

        if (!ObjectUtils.isEmpty(ip))
            query.addCriteria(Criteria.where("ip").is(ip));

        if (!ObjectUtils.isEmpty(token))
            query.addCriteria(Criteria.where("token").regex(token, "i"));

        if (!ObjectUtils.isEmpty(email))
            query.addCriteria(Criteria.where("email").regex(email, "i"));

        return query;
    }
}
