package mn.astvision.starter.repository.systemconfig;

import java.util.Optional;

import mn.astvision.starter.model.systemconfig.SystemApiConf;
import mn.astvision.starter.model.systemconfig.enums.SystemApiModuleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemApiConfRepository extends
    MongoRepository<SystemApiConf, SystemApiModuleType> {

    Optional<SystemApiConf> findByModuleType(SystemApiModuleType moduleType);
}
