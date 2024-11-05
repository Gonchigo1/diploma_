package mn.astvision.starter.service.article;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.article.ArticleException;
import mn.astvision.starter.model.article.ArticleCategory;
import mn.astvision.starter.repository.article.ArticleCategoryRepository;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleCategoryService {
    private final ArticleCategoryRepository categoryRepository;

    public ArticleCategory get(String id) throws ArticleException {
        ArticleCategory category = categoryRepository.findByIdAndDeletedFalse(id);
        if (category == null) {
            throw new ArticleException("Өгөгдөл олдсонгүй");
        }

        return category;
    }
}
