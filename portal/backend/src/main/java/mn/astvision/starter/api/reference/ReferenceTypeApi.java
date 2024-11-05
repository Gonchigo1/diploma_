package mn.astvision.starter.api.reference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.annotations.AuthUser;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.reference.ReferenceTypeDao;
import mn.astvision.starter.model.auth.User;
import mn.astvision.starter.model.reference.ReferenceType;
import mn.astvision.starter.repository.reference.ReferenceTypeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

/**
 * @author Gerelt-Od
 */
@Slf4j
@RestController
@Secured({"ROLE_REFERENCE_TYPE_VIEW", "ROLE_REFERENCE_TYPE_MANAGE"})
@RequestMapping("/v1/reference-type")
@RequiredArgsConstructor
public class ReferenceTypeApi {

    private final MongoTemplate mongoTemplate;
    private final ReferenceTypeRepository referenceTypeRepository;
    private final ReferenceTypeDao referenceTypeDao;

    @GetMapping
    public ResponseEntity<?> list(
            String name,
            String description,
            Boolean deleted,
            String sortOrder,
            String sortField,
            AntdPagination pagination) {
//        log.debug("Reference type list -> name: " + name + ", deleted: " + deleted + ", pagination: " + pagination);
        AntdTableDataList<ReferenceType> listData = new AntdTableDataList<>();
        String sort = "DESC";
        if (!ObjectUtils.isEmpty(sortOrder)) {
            if (sortOrder.equals("ascend")) {
                sort = "ASC";
            } else {
                sort = "DESC";
            }
        }

        pagination.setTotal(referenceTypeDao.count(description, name, deleted));
        listData.setPagination(pagination);
        listData.setList(referenceTypeDao.list(description, name, deleted,
                PageRequest.of(pagination.getCurrentPage(), pagination.getPageSize(),
                        Sort.by(
                                Sort.Direction.fromString(sort),
                                !ObjectUtils.isEmpty(sortField) ? sortField : "createdDate"))
        ));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select() {
        List<AggregationOperation> aggOperations = new ArrayList<>();

        aggOperations.add(Aggregation.match(Criteria.where("deleted").is(false)));
        aggOperations.add(sort(Sort.Direction.ASC, "createdDate"));

//            if (deleted != null) {
//                aggOperations.add(Aggregation.match(Criteria.where("deleted").is(deleted)));
//            }

        Aggregation selectAggregation = newAggregation(aggOperations);

        AggregationResults<ReferenceType> getAll = mongoTemplate.aggregate(
                selectAggregation,
                ReferenceType.class,
                ReferenceType.class);
        List<ReferenceType> result = getAll.getMappedResults();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok().body(referenceTypeRepository.findById(id).orElse(null));
    }

    @GetMapping("get-for-code/{code}")
    public ResponseEntity<?> getForCode(@PathVariable String code) {
        return ResponseEntity.ok().body(referenceTypeRepository.findByCode(code).orElse(null));
    }

    @Secured("ROLE_REFERENCE_TYPE_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ReferenceType createRequest, @AuthUser User user) {
        if (ObjectUtils.isEmpty(createRequest.getCode()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("code Хоосон байна.");

        if (referenceTypeRepository.existsByCodeAndDeletedFalse(createRequest.getCode().toUpperCase()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("code давхцаж байна.");

        log.debug("create -> " + createRequest);
        ReferenceType referenceType = new ReferenceType();
        referenceType.setName(createRequest.getName());
        referenceType.setCode(createRequest.getCode().toUpperCase());
        referenceType.setIcon(createRequest.getIcon());
        referenceType.setDescription(createRequest.getDescription());
        referenceType.setActive(createRequest.isActive());
        referenceType.setCreatedBy(user.getId());
        referenceType.setCreatedDate(LocalDateTime.now());
        referenceType = referenceTypeRepository.save(referenceType);

        return ResponseEntity.ok(referenceType.getId());
    }

    @Secured("ROLE_REFERENCE_TYPE_MANAGE")
    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody ReferenceType updateRequest, @AuthUser User user) {
        if (ObjectUtils.isEmpty(updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID хоосон байна.");

        Optional<ReferenceType> typeOptional = referenceTypeRepository.findById(updateRequest.getId());
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ReferenceType not found.");

        log.debug("update -> " + updateRequest);
        ReferenceType referenceType = typeOptional.get();
        referenceType.setName(updateRequest.getName());
        referenceType.setIcon(updateRequest.getIcon());
        referenceType.setDescription(updateRequest.getDescription());
        referenceType.setActive(updateRequest.isActive());
        referenceType.setModifiedBy(user.getId());
        referenceType.setModifiedDate(LocalDateTime.now());
        referenceType = referenceTypeRepository.save(referenceType);

        return ResponseEntity.ok(referenceType.getId());
    }

    @Secured("ROLE_REFERENCE_TYPE_MANAGE")
    @PostMapping("delete")
    public ResponseEntity<?> deleteMulti(@RequestParam String id, @AuthUser User user) {
        Optional<ReferenceType> typeOptional = referenceTypeRepository.findById(id);
        if (typeOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ReferenceData not found.");

        log.debug("delete id ->  " + id);
        ReferenceType referenceType = typeOptional.get();
        referenceType.setDeleted(true);
        referenceType.setActive(false);
        referenceType.setModifiedDate(LocalDateTime.now());
        referenceType.setModifiedBy(user.getId());

        referenceTypeRepository.save(referenceType);
        return ResponseEntity.ok().body(true);
    }
}
