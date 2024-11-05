package mn.astvision.starter.repository;

import mn.astvision.starter.model.BookType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookTypeRepository extends MongoRepository<BookType, String> {
    boolean existsByCodeAndDeletedFalse(String code);

    boolean existsByIdAndDeletedFalse(String id);

    Optional<BookType> findByIdAndDeletedFalse(String id);

    boolean existsByNameAndDeletedFalse(String name);

    boolean existsByOrder(Integer order);

    List<BookType> findAllByOrderGreaterThan(Integer order);

    Optional<BookType> findByCode(String code);
}
