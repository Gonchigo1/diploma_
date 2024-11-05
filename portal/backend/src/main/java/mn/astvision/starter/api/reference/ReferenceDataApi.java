package mn.astvision.starter.api.reference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mn.astvision.starter.api.BaseController;
import mn.astvision.starter.api.request.antd.AntdPagination;
import mn.astvision.starter.api.response.antd.AntdTableDataList;
import mn.astvision.starter.dao.reference.ReferenceDataDao;
import mn.astvision.starter.model.reference.ReferenceData;
import mn.astvision.starter.repository.reference.ReferenceDataRepository;
import mn.astvision.starter.service.reference.ReferenceDataService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

/**
 * @author Gerelt-Od
 */
@Slf4j
@RestController
@Secured({"ROLE_REFERENCE_DATA_VIEW", "ROLE_REFERENCE_DATA_MANAGE"})
@RequestMapping("/v1/reference-data")
@RequiredArgsConstructor
public class ReferenceDataApi extends BaseController {

    private final ReferenceDataRepository referenceDataRepository;
    private final ReferenceDataDao referenceDataDAO;
    private final ReferenceDataService referenceDataService;
    private final MongoTemplate mongoTemplate;

    @GetMapping
    public ResponseEntity<?> list(
            String typeCode,
            String name,
            String typeMean,
            String typeShortMean,
            String description,
            String sortOrder,
            String sortField,
            AntdPagination pagination) {

        AntdTableDataList<ReferenceData> listData = new AntdTableDataList<>();
        String sort;
        if (!ObjectUtils.isEmpty(sortOrder)) {
            if (sortOrder.equals("ascend")) {
                sort = "ASC";
            } else {
                sort = "DESC";
            }
        } else {
            sort = "ASC";
        }

        pagination.setTotal(referenceDataDAO.count(
                typeCode,
                name,
                typeMean,
                typeShortMean,
                description));
        listData.setPagination(pagination);
        listData.setList(referenceDataService.list(
                typeCode,
                name,
                typeMean,
                typeShortMean,
                description,
                PageRequest.of(
                        pagination.getCurrentPage(),
                        pagination.getPageSize(),
                        Sort.by(
                                Sort.Direction.fromString(sort),
                                !ObjectUtils.isEmpty(sortField) ? sortField : "order"))));

        return ResponseEntity.ok(listData);
    }

    @GetMapping("select")
    public ResponseEntity<?> select(String typeCode) {
        if (ObjectUtils.isEmpty(typeCode))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("typeCode Хоосон байна.");

        List<AggregationOperation> aggOperations = new ArrayList<>();
        aggOperations.add(Aggregation.match(Criteria.where("typeCode").is(typeCode)));
        aggOperations.add(Aggregation.match(Criteria.where("deleted").is(false)));
        aggOperations.add(sort(Sort.Direction.ASC, "order"));

        Aggregation selectAggregation = newAggregation(aggOperations);

        AggregationResults<ReferenceData> getAll = mongoTemplate.aggregate(
                selectAggregation,
                ReferenceData.class,
                ReferenceData.class);
        List<ReferenceData> result = getAll.getMappedResults();
        return ResponseEntity.ok(result);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok().body(referenceDataRepository.findById(id).orElse(null));
    }

    @Secured("ROLE_REFERENCE_DATA_MANAGE")
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ReferenceData createRequest) {
        if (ObjectUtils.isEmpty(createRequest.getTypeCode()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("typeCode Хоосон байна.");

        if (createRequest.getOrder() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order Хоосон байна.");

        if (referenceDataRepository.existsByTypeCodeAndDeletedFalse(createRequest.getTypeCode()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Өгөгдөл давхцаж байна: " + createRequest.getName());

        if (referenceDataRepository.existsByOrderAndTypeCodeAndDeletedFalse(
                createRequest.getOrder(), createRequest.getTypeCode())) {
            changeOrderFromCrate(createRequest);
        }

        log.debug("create -> " + createRequest);
        ReferenceData referenceData = new ReferenceData();
        referenceData.setTypeCode(createRequest.getTypeCode());
        referenceData.setActive(createRequest.isActive());
        referenceData.setName(createRequest.getName());
        referenceData.setCode(createRequest.getCode());
        referenceData.setDescription(createRequest.getDescription());
        referenceData.setOrder(createRequest.getOrder());
        referenceData.setIcon(createRequest.getIcon());
        referenceData = referenceDataRepository.save(referenceData);

        return ResponseEntity.ok(referenceData.getId());
    }

    @PostMapping("update")
    @Secured("ROLE_REFERENCE_DATA_MANAGE")
    public ResponseEntity<?> update(@RequestBody ReferenceData updateRequest) {
        if (ObjectUtils.isEmpty(updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID Хоосон байна.");
        if (ObjectUtils.isEmpty(updateRequest.getName()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("name Хоосон байна.");
        if (updateRequest.getOrder() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("order Хоосон байна.");

        if (referenceDataRepository.existsByTypeCodeAndIdNotAndDeletedFalse(
                updateRequest.getTypeCode(), updateRequest.getId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Өгөгдөл давхцаж байна: " + updateRequest.getName());

        Optional<ReferenceData> referenceDataOptional = referenceDataRepository.findById(updateRequest.getId());
        if (referenceDataOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ReferenceData not found.");

        log.debug("update -> " + updateRequest);
        ReferenceData referenceData = referenceDataOptional.get();
        changeOrderFromUpdate(referenceData, updateRequest);

        referenceData.setName(updateRequest.getName());
        referenceData.setCode(updateRequest.getCode());
        referenceData.setTypeCode(updateRequest.getTypeCode());
        referenceData.setActive(updateRequest.isActive());
        referenceData.setDescription(updateRequest.getDescription());
        referenceData.setOrder(updateRequest.getOrder());
        referenceData.setIcon(updateRequest.getIcon());
        referenceDataRepository.save(referenceData);

        return ResponseEntity.ok(referenceData.getId());
    }

    @Secured("ROLE_REFERENCE_DATA_MANAGE")
    @PostMapping("delete")
    public ResponseEntity<?> deleteMulti(@RequestParam String id) {
        Optional<ReferenceData> referenceDataOptional = referenceDataRepository.findById(id);
        if (referenceDataOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ReferenceData not found.");

        log.debug("delete id -> " + id);
        ReferenceData referenceData = referenceDataOptional.get();
        referenceData.setDeleted(true);
        referenceData.setActive(false);
        referenceData = referenceDataRepository.save(referenceData);
        changeOrderFromDelete(referenceData);

        return ResponseEntity.ok().body(null);
    }

    private void changeOrderFromDelete(ReferenceData updateRequest) {
        List<ReferenceData> referenceDatas = referenceDataRepository.
                findAllByTypeCodeAndOrderGreaterThanEqualAndDeletedFalseOrderByOrder(
                        updateRequest.getTypeCode(),
                        updateRequest.getOrder(),
                        Sort.by(Sort.Direction.ASC, "Order"));
        if (referenceDatas != null && !referenceDatas.isEmpty()) {
            for (ReferenceData referenceData : referenceDatas) {
                boolean check = referenceDataRepository.existsByOrderAndTypeCodeAndDeletedFalse(
                        referenceData.getOrder() + 1,
                        updateRequest.getTypeCode());
                referenceData.setOrder(referenceData.getOrder() - 1);
                referenceDataRepository.save(referenceData);
                if (!check) break;
            }
        }
    }

    private void changeOrderFromCrate(ReferenceData updateRequest) {
        List<ReferenceData> referenceDatas = referenceDataRepository
                .findAllByTypeCodeAndOrderGreaterThanEqualAndDeletedFalseOrderByOrder(
                        updateRequest.getTypeCode(),
                        updateRequest.getOrder(),
                        Sort.by(Sort.Direction.ASC, "Order"));
        if (referenceDatas != null && !referenceDatas.isEmpty()) {
            for (ReferenceData referenceData : referenceDatas) {
                referenceData.setOrder(referenceData.getOrder() + 1);
                boolean check = referenceDataRepository.existsByOrderAndTypeCodeAndDeletedFalse(
                        referenceData.getOrder(),
                        updateRequest.getTypeCode());
                referenceDataRepository.save(referenceData);
                if (!check) break;
            }
        }
    }

    private void changeOrderFromUpdate(ReferenceData oldReferenceData, ReferenceData updateRequest) {
        if (referenceDataRepository.existsByIdNotAndOrderAndTypeCodeAndDeletedFalse(
                updateRequest.getId(),
                updateRequest.getOrder(),
                updateRequest.getTypeCode())) {
            if (Objects.equals(oldReferenceData.getOrder(), updateRequest.getOrder())) {
                changeOrderFromCrate(updateRequest);
            } else {
                boolean isPlus = updateRequest.getOrder() > oldReferenceData.getOrder(); // jishee 1-g 5 bolgowol true
                Integer startOrder = updateRequest.getOrder() - 1;
                int endOrder = oldReferenceData.getOrder();
                if (isPlus) {
                    startOrder = oldReferenceData.getOrder();
                    endOrder = updateRequest.getOrder() + 1;
                }

                List<ReferenceData> referenceDataList = referenceDataRepository
                        .findAllByTypeCodeEqualsAndOrderBetweenAndDeletedFalseOrderByOrder(
                                updateRequest.getTypeCode(),
                                startOrder,
                                endOrder,
                                Sort.by(isPlus ? Sort.Direction.DESC : Sort.Direction.ASC, "Order"));
                if (referenceDataList != null && !referenceDataList.isEmpty()) {
                    List<ReferenceData> newList = new ArrayList<>();
                    for (ReferenceData referenceData : referenceDataList) {
                        if (isPlus)
                            referenceData.setOrder(referenceData.getOrder() - 1);
                        else
                            referenceData.setOrder(referenceData.getOrder() + 1);

                        newList.add(referenceData);
                        if (!referenceDataRepository.existsByOrderAndTypeCodeAndDeletedFalse(
                                referenceData.getOrder(),
                                updateRequest.getTypeCode()))
                            break;
                    }

                    referenceDataRepository.saveAll(newList);
                }
            }
        }
    }
}
