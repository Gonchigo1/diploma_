package mn.astvision.starter.repository.syslocale;

import mn.astvision.starter.model.syslocale.SysLocale;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysLocaleRepository extends MongoRepository<SysLocale, String> {

    boolean existsByIdAndDeletedFalse(String id);

    boolean existsByFieldAndLng(String field, String lng);

    Optional<SysLocale> findByFieldAndLng(String field, String lng);

    Optional<SysLocale> findByCode(String code);

    List<SysLocale> findAllByNsIdAndLngAndDeletedFalse(String ns, String lng);
}
