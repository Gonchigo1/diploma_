package mn.astvision.starter.dao.bank;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.model.bank.BankAccount;
import mn.astvision.starter.repository.bank.BankAccountRepository;
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
public class BankAccountDao {

    private final BankAccountRepository bankAccountRepository;
    private final MongoTemplate mongoTemplate;

    public long count(String bankCode, String search) {
        return mongoTemplate.count(buildQuery(bankCode, search), BankAccount.class);
    }

    public List<BankAccount> list(String bankCode, String search, PageRequest pageRequest) {
        Query query = buildQuery(bankCode, search);
        if (pageRequest == null)
            query = query.with(PageRequest.of(0, 10, Sort.Direction.ASC, "code"));
        else
            query = query.with(pageRequest);

        return mongoTemplate.find(query, BankAccount.class);
    }

    public List<BankAccount> select() {
        Query query = buildQuery(null, null);
        return mongoTemplate.find(query, BankAccount.class);
    }

    public Map<String, BankAccount> map(List<String> ids) {
        Map<String, BankAccount> bankAccountMap = new HashMap<>();

        List<BankAccount> bankAccounts = bankAccountRepository.findAllById(ids);
        for (BankAccount bankAccount : bankAccounts)
            bankAccountMap.put(bankAccount.getId(), bankAccount);

        return bankAccountMap;
    }

    private Query buildQuery(String bankCode, String search) {
        Query query = new Query();

        if (!ObjectUtils.isEmpty(bankCode))
            query.addCriteria(Criteria.where("bankCode").is(bankCode));

        query.addCriteria(Criteria.where("deleted").is(false));

        if (!ObjectUtils.isEmpty(search)) {
            query.addCriteria(new Criteria().orOperator(
                    Criteria.where("accountNumber").regex(search, "i"),
                    Criteria.where("accountName").regex(search, "i")
            ));
        }

        return query;
    }
}
