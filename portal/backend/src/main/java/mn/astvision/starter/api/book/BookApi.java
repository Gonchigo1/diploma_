package mn.astvision.starter.api.book;

import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;

import mn.astvision.starter.dao.book.BookDao;
import mn.astvision.starter.model.Teacher;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.book.Book;
import mn.astvision.starter.repository.book.BookRepository;
import mn.astvision.starter.service.book.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/book")
public class BookApi {
    private final BookService bookService;
    private final BookRepository bookRepository;

    public BookApi(
            BookService BookService,
            BookRepository BookRepository

    )
    {
        this.bookService = BookService;
        this.bookRepository = BookRepository;
    }    @GetMapping

    public ResponseEntity<?> list(
            String name,
            String type,
            Boolean deleted,
            AntdPagination pagination
    ) {
        AntdTableDataList<Book> listData = new AntdTableDataList<>();

        pagination.setTotal(
                bookService.count(
                        name,
                        type,
                        deleted
                )
        );

        listData.setPagination(pagination);
        listData.setList(
                bookService.list(
                        name,
                        type,
                        deleted,
                        pagination.toPageRequest())
        );

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?>
        select(
                String name,
                String type,
                Boolean deleted

        ) {
            Iterable<Book> listData = bookService.list(
                    name,
                    type,
                    deleted,
                    null
            );
            return ResponseEntity.ok(listData);
        }


    @GetMapping("{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @AuthUser User user) {
        log.debug("Attempting to delete book with ID: " + id);
        try {
            Book book = bookRepository.findByIdAndDeletedFalse(id);
            if (book != null) {
                book.setDeleted(true);
                book.setModifiedBy(user.getId());
                book.setCreatedDate(LocalDateTime.now());
                bookRepository.save(book);
                return ResponseEntity.ok(book);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("book not found");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}