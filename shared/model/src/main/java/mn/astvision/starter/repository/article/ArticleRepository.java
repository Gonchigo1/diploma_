package mn.astvision.starter.repository.article;

import java.util.List;

import mn.astvision.starter.model.article.Article;
import org.springframework.data.mongodb.core.mapping.Unwrapped.Nullable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author digz6666
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

    @Query(value = "{'categoryId': ?0, 'deleted': false}")
    List<Article> findAllByCategoryId(String categoryId);

    @Query(value = "{'status': ?0, 'deleted': false}")
    List<Article> findAllByStatus(String status);

    @Query(value = "{'categoryId': ?0, 'status': ?1, 'deleted': false}")
    List<Article> findAllByCategoryIdAndStatus(String categoryId, String status);

    @Nullable
    Article findByIdAndDeletedFalse(String id);

    boolean existsByTitleAndDeletedFalse(String title);
}
