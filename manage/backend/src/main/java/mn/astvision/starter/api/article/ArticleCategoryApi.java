package mn.astvision.starter.api.article;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.exception.article.ArticleException;
import mn.astvision.starter.model.article.ArticleCategory;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.article.ArticleCategoryRepository;
import mn.astvision.starter.service.article.ArticleCategoryCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author digz6666
 */
@Slf4j
@RestController
@Secured({"ROLE_ARTICLE_VIEW", "ROLE_ARTICLE_MANAGE"})
@RequestMapping("/v1/article-category")
@RequiredArgsConstructor
public class ArticleCategoryApi extends BaseController {

    private final ArticleCategoryCrudService categoryCrudService;
    private final ArticleCategoryRepository categoryRepository;

    @GetMapping("select")
    public List<?> select() {
        return categoryRepository.findAllDeletedFalse();
    };

    @Secured("ROLE_ARTICLE_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleCategory requestCategory, @AuthUser User user) {
        try {
            requestCategory.setCreatedBy(user.getId());
            requestCategory.setCreatedDate(LocalDateTime.now());

            return ResponseEntity.ok(categoryCrudService.create(requestCategory));
        } catch (ArticleException e) {
            log.error(e.getMessage());
            return badRequestMessage(e.getMessage());
        }
    }

    @Secured("ROLE_ARTICLE_MANAGE")
    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody ArticleCategory requestCategory, @AuthUser User user) {
        try {
            requestCategory.setModifiedBy(user.getId());
            requestCategory.setModifiedDate(LocalDateTime.now());

            return ResponseEntity.ok(categoryCrudService.update(requestCategory));
        } catch (ArticleException e) {
            log.error(e.getMessage());
            return badRequestMessage(e.getMessage());
        }
    }

    @Secured("ROLE_ARTICLE_MANAGE")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @AuthUser User user) {
        try {
            return ResponseEntity.ok(categoryCrudService.delete(id, user.getId()));
        } catch (ArticleException e) {
            log.error(e.getMessage());
            return badRequestMessage(e.getMessage());
        }
    }
}
