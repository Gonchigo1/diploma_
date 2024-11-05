package mn.astvision.starter.api.oxfordThinkers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.oxfordThinkers.OxfordThinkersDao;
import mn.astvision.starter.model.oxfordThinkers.Topic;
import mn.astvision.starter.repository.oxfordThinkers.TopicRepository;
import mn.astvision.starter.service.oxfordThinkers.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/oxfordThinkers")
public class OxfordThinkersApi extends BaseController {

    private final TopicRepository topicRepository;
    private final TopicService topicService;
    private final OxfordThinkersDao oxfordThinkersDao;

    @GetMapping
    public ResponseEntity<?> list(
            String bookId,
            String name,
            AntdPagination pagination
    ) {
        AntdTableDataList<Topic> listData = new AntdTableDataList<>();
        pagination.setTotal
                (oxfordThinkersDao.count(
                        bookId,
                        name
                        ));
        listData.setPagination(pagination);
        listData.setList(
                topicService.list(
                        bookId,
                        name,
                        pagination.toPageRequest()
                )
        );
        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<List<Topic>> getAllOxfordItems() {
        List<Topic> oxfordItems = topicService.findAll();
        return ResponseEntity.ok(oxfordItems);
    }

    @GetMapping("{id}")
    public String getById(@PathVariable String id) {
        System.out.println("GET ");
        return topicRepository.findByIdAndDeletedFalse(id).getName();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.debug("Attempting to delete Oxford thinker with ID: " + id);
        if (!topicRepository.existsById(id)) {
            log.warn("Oxford thinker with ID: " + id + " not found.");
            return ResponseEntity.notFound().build();
        }
        topicService.deleteById(id);
        log.info("Successfully deleted Oxford thinker with ID: " + id);
        return ResponseEntity.noContent().build();
    }


}
