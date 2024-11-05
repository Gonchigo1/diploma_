package mn.astvision.starter.service;

import lombok.RequiredArgsConstructor;
import mn.astvision.starter.dao.BookTypeDao;
import mn.astvision.starter.model.BookType;
import mn.astvision.starter.repository.BookTypeRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class BookTypeService {
    private final BookTypeDao bookTypeDao;
    private final BookTypeRepository bookTypeRepository;

    public Iterable<BookType> list(String name, String code, Boolean active, Pageable pageable) {
        Iterable<BookType> listData = bookTypeDao.list(name, code, active, pageable);

        for (BookType locale : listData) {
            fillRelatedData(locale);
        }

        return listData;
    }

    private void fillRelatedData(BookType bookType) {

        if (!ObjectUtils.isEmpty(bookType.getCode()))
            bookTypeRepository.findByCode(bookType.getCode());
    }
}
