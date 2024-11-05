package mn.astvision.starter.repository.currency;

import mn.astvision.starter.model.currency.CurrencyRate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CurrencyRateRepository extends MongoRepository<CurrencyRate, String> {

    boolean existsByDayAndFromAndTo(LocalDate day, String from, String to);
}
