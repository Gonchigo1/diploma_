package mn.astvision.starter.dao.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.payment.Payment;
import mn.astvision.starter.model.payment.enums.PaymentStatus;
import mn.astvision.starter.model.payment.enums.PaymentType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PaymentDao {

    private final MongoTemplate mongoTemplate;

    public long count(
            LocalDate startDate,
            LocalDate endDate,
            PaymentType type,
            String payerId,
            PaymentStatus status,
            String number,
            String contactPhone) {
        if (type == null
                && ObjectUtils.isEmpty(payerId)
                && status == null
                && ObjectUtils.isEmpty(number)) {
            return mongoTemplate.estimatedCount(Payment.class);
        }

        return mongoTemplate.count(buildQuery(
                startDate,
                endDate,
                type,
                payerId,
                status,
                number,
                contactPhone
        ), Payment.class);
    }

    public Iterable<Payment> list(
            LocalDate startDate,
            LocalDate endTime,
            PaymentType type,
            String payerId,
            PaymentStatus status,
            String number,
            String contactPhone,
            Pageable pageable) {
        Query query = buildQuery(
                startDate,
                endTime,
                type,
                payerId,
                status,
                number,
                contactPhone);
        if (pageable != null)
            query.with(pageable);

//        query.with(Sort.by("paidDate").descending());

        return mongoTemplate.find(query, Payment.class);
    }

    private Query buildQuery(
            LocalDate startDate,
            LocalDate endDate,
            PaymentType paymentType,
            String payerId,
            PaymentStatus status,
            String number,
            String contactPhone) {
        Query query = new Query();

        if (startDate != null && endDate != null)
            query.addCriteria(
                    new Criteria().andOperator(
                            Criteria.where("createdDate").gte(startDate.atTime(LocalTime.MIN)),
                            Criteria.where("createdDate").lte(endDate.atTime(LocalTime.MAX))
                    )
            );

        if (number != null)
            query.addCriteria(Criteria.where("number").is(number));

        if (contactPhone != null)
            query.addCriteria(Criteria.where("contactPhone").is(contactPhone));

        if (paymentType != null)
            query.addCriteria(Criteria.where("paymentType").is(paymentType));

        if (!ObjectUtils.isEmpty(payerId))
            query.addCriteria(Criteria.where("payerId").is(payerId));

        if (status != null)
            query.addCriteria(Criteria.where("status").is(status));

//        query.addCriteria(Criteria.where("deleted").is(deleted != null ? deleted : false));
//        if (deleted != null)
//            query.addCriteria(Criteria.where("deleted").is(deleted));

        return query;
    }
}
