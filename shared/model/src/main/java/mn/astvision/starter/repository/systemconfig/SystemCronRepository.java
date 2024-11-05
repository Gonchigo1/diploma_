package mn.astvision.starter.repository.systemconfig;

import mn.astvision.starter.model.systemconfig.SystemCron;
import mn.astvision.starter.model.systemconfig.enums.SystemCronType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MethoD
 */
@Repository
public interface SystemCronRepository extends MongoRepository<SystemCron, SystemCronType> {

    @Nullable
    SystemCron findByCronType(SystemCronType cronType);

    List<SystemCron> findAllByCronTypeIn(List<SystemCronType> cronType);
}
