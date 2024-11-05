package mn.astvision.starter.service.article;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.exception.article.ArticleException;
import mn.astvision.starter.model.article.Article;
import mn.astvision.starter.repository.article.ArticleCategoryRepository;
import mn.astvision.starter.repository.article.ArticleRepository;
import mn.astvision.starter.repository.auth.UserRepository;

/**
 * @author digz6666
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleCategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Article get(String id) throws ArticleException {
        Article article = articleRepository.findByIdAndDeletedFalse(id);
        if (article == null) {
            throw new ArticleException("Өгөгдөл олдсонгүй");
        }

        return fillData(article);
    }

    public Article fillData(Article article) {
        if (ObjectUtils.isEmpty(article)) {
            return article;
        }

        // fill category
        article.setCategory(categoryRepository.findByIdAndDeletedFalse(article.getCategoryId()));

        // fill publisher
        article.setPublisher(userRepository.findByIdAndDeletedFalse(article.getCreatedBy()));

        return article;
    }

}
