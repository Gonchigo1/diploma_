package mn.astvision.starter.repository.book;
import mn.astvision.starter.model.book.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Book findByIdAndDeletedFalse(String code);
}
