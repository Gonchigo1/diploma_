package mn.astvision.starter.api.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.BookTypeDao;
import mn.astvision.starter.model.BookType;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.repository.BookTypeRepository;
import mn.astvision.starter.service.BookTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
//@Secured({"ROLE_ARTICLE_VIEW", "ROLE_ARTICLE_MANAGE"})
@RestController
@RequestMapping("/v1/book-type")
@RequiredArgsConstructor
public class BookTypeApi extends BaseController {
    private final BookTypeRepository bookTypeRepository;
    private final BookTypeDao bookTypeDao;
    private final BookTypeService bookTypeService;

    @GetMapping
    public ResponseEntity<?> list(
            String name,
            String code,
            Boolean active,
            AntdPagination pagination
    ) {
        AntdTableDataList<BookType> listData = new AntdTableDataList<>();

        pagination.setTotal(
                bookTypeDao.count(
                        name,
                        code,
                        active
                )
        );
        listData.setPagination(pagination);
        listData.setList(
                bookTypeService.list(
                        name,
                        code,
                        active,
                        pagination.toPageRequest()
                )
        );

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(
            String name,
            String code,
            Boolean active
    ) {
        Iterable<BookType> listData = bookTypeDao.list(
                name,
                code,
                active,
                null
        );
        return ResponseEntity.ok(listData);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok().body(bookTypeRepository.findByIdAndDeletedFalse(id).orElse(null));
    }
//    @PostMapping("update")
//    public ResponseEntity<?> update(
//            @RequestBody BookType updateRequest,
//            @AuthUser User user
//    ) {
//        if (ObjectUtils.isEmpty(updateRequest.getId()))
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID хоосон байна.");
//
//        Optional<BookType> typeOptional = bookTypeRepository.findByIdAndDeletedFalse(updateRequest.getId());
//        if (typeOptional.isEmpty())
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("BookType not found.");
//
//        log.debug("updating BookType -> " + updateRequest);
//        BookType type = typeOptional.get();
//
//        type.setName(updateRequest.getName());
//        type.setOrder(updateRequest.getOrder());
//        type.setCode(updateRequest.getCode());
//        type.setModifiedBy(user.getId());
//        type.setModifiedDate(LocalDateTime.now());
//
//        type = bookTypeRepository.save(type);
//
//        return ResponseEntity.ok(type);
//    }

}
