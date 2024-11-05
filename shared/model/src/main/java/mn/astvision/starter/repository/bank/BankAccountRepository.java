package mn.astvision.starter.repository.bank;

import mn.astvision.starter.model.bank.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author digz6666
 */
@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
}
