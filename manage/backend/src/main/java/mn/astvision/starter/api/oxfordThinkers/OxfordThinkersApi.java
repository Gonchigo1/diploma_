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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/oxfordThinkers")
public class OxfordThinkersApi extends BaseController {

    private final TopicRepository topicRepository;
    private final TopicService topicService;
    private final OxfordThinkersDao oxfordThinkersDao;

    @GetMapping()
    public ResponseEntity<?> list(
            String bookId,
            String name,
            String sortOrder,
            String sortField,
            AntdPagination pagination
   ) {
        try {
            AntdTableDataList<Topic> dataList = new AntdTableDataList<>();

            if (Objects.equals(sortOrder, "descend"))
                pagination.setSortDirection(Sort.Direction.DESC);
            else
                pagination.setSortDirection(Sort.Direction.ASC);

            if (ObjectUtils.isEmpty(sortField))
                pagination.setSortParam("id");
            else
                pagination.setSortParam(sortField);

            pagination.setTotal(
                    oxfordThinkersDao.count(
                            bookId,
                            name
                    )
            );
            dataList.setPagination(pagination);

            Iterable<Topic> oxfordThinkers = oxfordThinkersDao.list(
                    bookId,
                    name,
                    pagination.toPageRequest()
            );

            for (Topic topic1 : oxfordThinkers) {
                topicService.fillRelatedData(topic1);
            }

            dataList.setList(oxfordThinkers);
            return ResponseEntity.ok(dataList);
        } catch (Exception e) {
            log.error("ERROR "+ e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("create")
    public ResponseEntity<Topic> createOxfordItem(@RequestBody Topic oxfordItem) {
        Topic savedItem = topicService.save(oxfordItem);
        return ResponseEntity.ok(savedItem);
    }
    // Endpoint to get all Oxford Thinkers items
//    @GetMapping()
//    public ResponseEntity<List<OxfordThinkers>> getAllOxfordItems() {
//        List<OxfordThinkers> oxfordItems = oxfordService.findAll();
//        return ResponseEntity.ok(oxfordItems);
//    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        System.out.println("GET ");
        return ResponseEntity.ok(topicRepository.findByIdAndDeletedFalse(id));
    }

    @PostMapping("update")
    @Secured("ROLE_REFERENCE_DATA_MANAGE")
    public ResponseEntity<?> update(@RequestBody Topic updateRequest) {
        Optional<Topic> referenceDataOptional = topicRepository.findById(updateRequest.getId());
        if (referenceDataOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oxford thinker not found.");

        Topic topic = referenceDataOptional.get();
        topic.setName(updateRequest.getName());
//        oxfordThinkers.setBookId(updateRequest.getBookId());

        topicRepository.save(topic);
        return ResponseEntity.ok(topic);
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
