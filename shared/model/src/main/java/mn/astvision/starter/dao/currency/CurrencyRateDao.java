package mn.astvision.starter.dao.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.model.currency.CurrencyRate;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CurrencyRateDao {

    private final MongoTemplate mongoTemplate;

    public CurrencyRate getRate(String from, String to) {
        return mongoTemplate.findOne(
                new Query()
                        .addCriteria(Criteria.where(CurrencyRate.Fields.from).is(from))
                        .addCriteria(Criteria.where(CurrencyRate.Fields.to).is(to))
                        .with(PageRequest.of(0, 1, Sort.Direction.DESC, CurrencyRate.Fields.day)),
                CurrencyRate.class
        );
    }
}
