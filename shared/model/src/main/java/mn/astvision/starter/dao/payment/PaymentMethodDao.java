package mn.astvision.starter.dao.payment;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.payment.PaymentMethod;
import mn.astvision.starter.model.payment.enums.PaymentType;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PaymentMethodDao {

    private final MongoTemplate mongoTemplate;

    public List<PaymentMethod> list(PaymentType paymentType, Boolean requireLogin, Boolean visible) {
        Query query = new Query();

        if (paymentType != null)
            query.addCriteria(Criteria.where("paymentTypes").is(paymentType));
        if (visible != null)
            query.addCriteria(Criteria.where("visible").is(visible));
        if (requireLogin != null)
            query.addCriteria(Criteria.where("requireLogin").is(requireLogin));

        query.with(Sort.by("order"));

        return mongoTemplate.find(query, PaymentMethod.class);
    }
}
