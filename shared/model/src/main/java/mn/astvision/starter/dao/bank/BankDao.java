package mn.astvision.starter.dao.bank;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.bank.Bank;
import mn.astvision.starter.repository.bank.BankRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author digz6666
 */
@Repository
@RequiredArgsConstructor
public class BankDao {

    private final BankRepository bankRepository;
    private final MongoTemplate mongoTemplate;

    public long count(String code, String name) {
        return mongoTemplate.count(buildQuery(code, name), Bank.class);
    }

    public List<Bank> list(String code, String name, PageRequest pageRequest) {
        Query query = buildQuery(code, name);
        if (pageRequest == null)
            query = query.with(PageRequest.of(0, 10, Sort.Direction.ASC, "bankCode"));
        else
            query = query.with(pageRequest);

        return mongoTemplate.find(query, Bank.class);
    }

    public Map<String, Bank> map(List<String> codes) {
        Map<String, Bank> bankMap = new HashMap<>();

        List<Bank> banks = bankRepository.findByDeletedFalseAndCodeIn(codes);
        for (Bank bank : banks)
            bankMap.put(bank.getCode(), bank);

        return bankMap;
    }

    private Query buildQuery(String code, String name) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(code))
            query.addCriteria(Criteria.where("code").is(code));

        query.addCriteria(Criteria.where("deleted").is(false));

        if (!ObjectUtils.isEmpty(name))
            query.addCriteria(Criteria.where("name").regex(name, "i"));

        return query;
    }
}
