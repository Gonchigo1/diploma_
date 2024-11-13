//package mn.astvision.starter.api.article;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import mn.astvision.starter.annotations.AuthUser;
//import mn.astvision.starter.api.BaseController;
//import mn.astvision.starter.api.request.antd.AntdPagination;
//import mn.astvision.starter.api.response.antd.AntdTableDataList;
//import mn.astvision.starter.dao.article.ArticleDao;
//import mn.astvision.starter.exception.article.ArticleException;
//import mn.astvision.starter.model.article.Article;
//import mn.astvision.starter.model.article.enums.ArticleStatus;
//import mn.astvision.starter.model.auth.User;
//import mn.astvision.starter.service.article.ArticleCrudService;
//import mn.astvision.starter.service.article.ArticleService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author digz6666
// */
//@Slf4j
//@RestController
//@Secured({"ROLE_ARTICLE_VIEW", "ROLE_ARTICLE_MANAGE"})
//@RequestMapping("/v1/article")
//@RequiredArgsConstructor
//public class ArticleApi extends BaseController {
//
//    private final ArticleCrudService articleCrudService;
//    private final ArticleDao articleDao;
//    private final ArticleService articleService;
//
//    @GetMapping("")
//    public ResponseEntity<?> list(
//            String search,
//            String categoryId,
//            Boolean isFeatured,
//            ArticleStatus status,
//            AntdPagination pagination) {
//        AntdTableDataList<Article> dataList = new AntdTableDataList<>();
//
//        pagination.setTotal(articleDao.count(search, categoryId, isFeatured, status));
//        dataList.setPagination(pagination);
//        List<Article> articleList = articleDao.list(search, categoryId, isFeatured, status,
//                pagination.toPageRequest());
//
//        dataList.setList(articleList.stream().map(articleService::fillData).collect(Collectors.toList()));
//
//        return ResponseEntity.ok(dataList);
//    }
//
//    @GetMapping("published")
//    public ResponseEntity<?> publishedList(
//            String search,
//            String categoryId,
//            Boolean isFeatured,
//            AntdPagination pagination) {
//        AntdTableDataList<Article> dataList = new AntdTableDataList<>();
//
//        pagination.setTotal(articleDao.count(
//                search,
//                categoryId,
//                isFeatured,
//                ArticleStatus.PUBLISHED));
//        dataList.setPagination(pagination);
//        List<Article> articleList = articleDao.list(
//                search,
//                categoryId,
//                isFeatured,
//                ArticleStatus.PUBLISHED,
//                pagination.toPageRequest());
//
//        dataList.setList(articleList.stream().map(articleService::fillData).collect(Collectors.toList()));
//
//        return ResponseEntity.ok(dataList);
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> get(@PathVariable String id) {
//        try {
//            return ResponseEntity.ok(articleService.get(id));
//        } catch (ArticleException e) {
//            return badRequestMessage(e.getMessage());
//        }
//    }
//
//    @Secured("ROLE_ARTICLE_MANAGE")
//    @PostMapping("create")
//    public ResponseEntity<?> create(@Valid @RequestBody Article requestArticle, @AuthUser User user) {
//        try {
//            requestArticle.setCreatedBy(user.getId());
//            requestArticle.setCreatedDate(LocalDateTime.now());
//
//            return ResponseEntity.ok(articleCrudService.create(requestArticle));
//        } catch (ArticleException e) {
//            log.error(e.getMessage());
//            return badRequestMessage(e.getMessage());
//        }
//    }
//
//    @Secured("ROLE_ARTICLE_MANAGE")
//    @PutMapping("update")
//    public ResponseEntity<?> update(@Valid @RequestBody Article requestArticle, @AuthUser User user) {
//        try {
//            requestArticle.setModifiedBy(user.getId());
//            requestArticle.setModifiedDate(LocalDateTime.now());
//
//            return ResponseEntity.ok(articleCrudService.update(requestArticle));
//        } catch (ArticleException e) {
//            log.error(e.getMessage());
//            return badRequestMessage(e.getMessage());
//        }
//    }
//
//    @Secured("ROLE_ARTICLE_MANAGE")
//    @DeleteMapping("{id}")
//    public ResponseEntity<?> delete(@PathVariable String id, @AuthUser User user) {
//        try {
//            return ResponseEntity.ok(articleCrudService.delete(id, user.getId()));
//        } catch (ArticleException e) {
//            log.error(e.getMessage());
//            return badRequestMessage(e.getMessage());
//        }
//    }
//}
