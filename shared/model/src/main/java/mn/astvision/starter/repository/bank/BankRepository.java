package mn.astvision.starter.repository.bank;

import mn.astvision.starter.model.bank.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author digz6666
 */
@Repository
public interface BankRepository extends MongoRepository<Bank, String> {

    List<Bank> findByDeletedFalseOrderByName();

    List<Bank> findByDeletedFalseAndCodeIn(List<String> codes);
}
